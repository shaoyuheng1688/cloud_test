package com.raymon.taxguide.service.imp;
import com.raymon.taxguide.event.PollTimeEvent;
import com.raymon.taxguide.lock.ResourceLockFactory;
import com.raymon.taxguide.model.*;
import com.raymon.taxguide.remote.CsFeignClient;
import com.raymon.taxguide.remote.EFormFeignClient;
import com.raymon.taxguide.pojo.cs.CsServerParams;
import com.raymon.taxguide.repository.BusinessRepository;
import com.raymon.taxguide.repository.TaxguideRepository;
import com.raymon.taxguide.repository.UserRepository;
import com.raymon.taxguide.service.TaxpayerService;
import com.raymon.taxguide.util.ResultUtil;
import com.raymon.taxguide.utils.DateTimeUtils;
import com.raymon.taxguide.vo.CSTicketVo;
import com.raymon.taxguide.vo.EformRecordVo;
import com.raymon.taxguide.vo.GsonObjResult;
import com.raymon.taxguide.web.exception.ApplicationException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@Data
@PropertySource(value = {"classpath:/application.yml"}, encoding = "utf-8")
@ConfigurationProperties(prefix = "limit")
public class TaxpayerServiceImp implements TaxpayerService {

    /**
     * 票池标记
     */
    private static final int TAXGUIDE_CHANNEL = 5;
    private static final String TAXGUIDE_CHANNEL_CODE = "TAXGUIDE";

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    ResourceLockFactory lockFactory;
    @Autowired
    private EFormFeignClient eFormFeignClient;
    @Autowired
    private CsFeignClient csFeignClient;
    @Autowired
    private TaxguideRepository taxguideRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BusinessRepository businessRepository;

    private int maxWaitCount;

    private List<String> workTime;

    @Override
    public TaxguideRecord getTaxguideTask(String bussId) {
        TaxguideRecord taxguideRecord = taxguideRepository.findNotFinishTaxguideRecordByBussId(bussId);
        if(null == taxguideRecord){
            //创建导税任务
            taxguideRecord = createTaxguideTask(bussId);
        }
        return taxguideRecord;
    }

    @Override
    public TaxguideRecord ready(String bussId) {
        TaxguideRecord taxguideRecord = taxguideRepository.findNotFinishTaxguideRecordByBussId(bussId);
        if(null == taxguideRecord
                || TaxguideRecord.State.CALL != taxguideRecord.getState()){
            throw new ApplicationException("任务开始失败，导税任务为空或任务状态不对！");
        }
        TaxguideRecordInfo taxguideRecordInfo = taxguideRepository.findTaxguideRecordInfoById(taxguideRecord.getCurrentTgInfoId());
        taxguideRecord.setState(TaxguideRecord.State.READY);
        taxguideRecordInfo.setState(TaxguideRecord.State.READY);
        taxguideRepository.updateTaxguideRecordInfo(taxguideRecordInfo);
        return taxguideRepository.updateTaxguideRecord(taxguideRecord);
    }

    @Override
    public TaxguideRecord getState(String tgId) {
        TaxguideRecord taxguideRecord = taxguideRepository.findTaxguideRecordById(tgId);
        if(null == taxguideRecord){
            throw new ApplicationException("请输入正确tgId");
        }
        TaxguideRecordInfo taxguideRecordInfo =
                taxguideRepository.findTaxguideRecordInfoById(taxguideRecord.getCurrentTgInfoId());
        taxguideRecord.setCurrentTaxguideRecordInfo(taxguideRecordInfo);
        if(TaxguideRecord.State.WAIT == taxguideRecord.getState()
                && StringUtils.isNotBlank(taxguideRecordInfo.getCsTicketId())){
            taxguideRecord.setWaitCount(getWaitCountByCSTicketId(taxguideRecordInfo.getCsTicketId()));
        }
        eventPublisher.publishEvent(new PollTimeEvent(taxguideRecord, LogTaxguidePollTime.Source.NSR));
        return taxguideRecord;
    }

    @Override
    public void feedback(long tgInfoId, String remark) {
        TaxguideRecordInfo taxguideRecordInfo = taxguideRepository.findTaxguideRecordInfoById(tgInfoId);
        if(null == taxguideRecordInfo){
            throw new ApplicationException("记录不存在！");
        }
        taxguideRecordInfo.setRemark(remark);
        taxguideRepository.updateTaxguideRecordInfo(taxguideRecordInfo);
    }

    private TaxguideRecord createTaxguideTask(String bussId){
        if(!checkWorkTime()){
            String workTimes = StringUtils.join(workTime,",");
            throw new ApplicationException("非工作时间不能发起人工导税！导税时间段为工作日的:"+workTimes);
        }
        GsonObjResult<EformRecordVo> eformResult = eFormFeignClient.findEformRecordByRecordId(bussId);
        EformRecordVo eformRecord = ResultUtil.getResultData(eformResult);
        if(StringUtils.isBlank(eformRecord.getSwjgDm())){
            throw new ApplicationException("税务机构代码为空！bussId:" + bussId);
        }
        TaxguideRecord taxguideRecord =
                new TaxguideRecord(bussId,eformRecord.getSwjgDm(),eformRecord.getThemeCode(),eformRecord.getUserCard());
        taxguideRecord = taxguideRepository.saveTaxguideRecord(taxguideRecord);
        CSTicketVo csTicket = putTicketToPool(taxguideRecord.getSwjgDm(),taxguideRecord.getZtbm(),taxguideRecord.getTgId());
        TaxguideRecordInfo taxguideRecordInfo =
                new TaxguideRecordInfo(taxguideRecord.getTgId(),csTicket.getCsTicketId());
        taxguideRecordInfo = taxguideRepository.saveTaxguideRecordInfo(taxguideRecordInfo);
        taxguideRecord.setCurrentTgInfoId(taxguideRecordInfo.getTgInfoId());
        taxguideRecord.setWaitCount(getWaitCountByCSTicketId(taxguideRecordInfo.getCsTicketId()));
        return taxguideRepository.updateTaxguideRecord(taxguideRecord);
    }

    private CSTicketVo putTicketToPool(String swjgDm,String ztbm,String tgId){
        //入队参数
        CsServerParams csServerParams =  CsServerParams
                .newBuilder()
                .swjgDm(swjgDm)
                .ztbm("0")// 后台又说不用这个参数
                .queueChannelId(TAXGUIDE_CHANNEL)
                .bussId(tgId)
                .build();
        GsonObjResult<CSTicketVo> csResult = csFeignClient.putTicketToPool(csServerParams);
        CSTicketVo csTicket = ResultUtil.getResultData(csResult);
        return csTicket;
    }

    private int getWaitCountByCSTicketId(String cstId){
        int waitCount = 0;
        CsServerParams csServerParams =  CsServerParams.newBuilder().cstId(cstId).build();
        GsonObjResult<Map<String, Integer>> csResult = csFeignClient.getWaitCountByCSTicketId(csServerParams);
        Map<String, Integer> map = ResultUtil.getResultData(csResult);
        Integer count = map.get("waitCount");
        if(null != count){
            waitCount = count;
        }
        return waitCount;
    }

     private boolean checkWorkTime(){
        boolean isPass = false;
        WorkCalendar workCalendar = businessRepository.findWorkDayByDate(new Date());
        if(1 != workCalendar.getIsWorkday()){
            return isPass;
        }
        Date now = DateTimeUtils.parseTime(DateTimeUtils.formatTime(new Date()));
        for(String t : workTime){
            String[] timeSlice = t.split("~");
            Date beginTime = DateTimeUtils.parseTime(timeSlice[0]);
            Date endTime = DateTimeUtils.parseTime(timeSlice[1]);
            if(now.after(beginTime) && now.before(endTime)){
                isPass = true;
            }
        }
        return isPass;
    }

}
