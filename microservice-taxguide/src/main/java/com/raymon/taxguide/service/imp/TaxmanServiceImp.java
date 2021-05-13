package com.raymon.taxguide.service.imp;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
//import com.raymon.frame.lock.ResourceLock;
//import com.raymon.frame.lock.ResourceLockFactory;
//import com.raymon.frame.vo.GsonObjResult;
//import com.raymon.frame.vo.PageResultVO;
//import com.raymon.frame.web.exception.ApplicationException;
import com.raymon.taxguide.event.CallMessageEvent;
import com.raymon.taxguide.event.PollTimeEvent;
import com.raymon.taxguide.manager.TaxmanAgentActionManager;
import com.raymon.taxguide.model.LogTaxguidePollTime;
import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.model.TaxguideRecord;
import com.raymon.taxguide.model.TaxguideRecordInfo;
import com.raymon.taxguide.pojo.NsrAndTaskInfo;
import com.raymon.taxguide.pojo.TaxguideClientInfo;
import com.raymon.taxguide.pojo.cs.CsServerParams;
import com.raymon.taxguide.remote.CsFeignClient;
import com.raymon.taxguide.repository.TaxguideRepository;
import com.raymon.taxguide.repository.UserRepository;
import com.raymon.taxguide.service.TaxmanService;
import com.raymon.taxguide.action.ActionEvent;
import com.raymon.taxguide.action.AbstractAction;
import com.raymon.taxguide.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TaxmanServiceImp implements TaxmanService {
//
//    /**
//     * 票池标记
//     */
//    private static final int TAXGUIDE_CHANNEL = 5;
//    private static final String TAXGUIDE_CHANNEL_CODE = "TAXGUIDE";
//
//    @Autowired
//    private ApplicationEventPublisher eventPublisher;
//    @Autowired
//    CsFeignClient csFeignClient;
//    @Autowired
//    ResourceLockFactory lockFactory;
//    @Autowired
//    TaxmanAgentActionManager taxmanAgentActionManager;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    private TaxguideRepository taxguideRepository;
//
//    /**
//     * 客户端本地锁
//     */
//    private Cache<String, ResourceLock> taxmanLockCache = CacheBuilder
//                                                            .newBuilder()
//                                                            .weakValues()
//                                                            .expireAfterWrite(4 * 3600L, TimeUnit.SECONDS)
//                                                            .expireAfterAccess(4 * 3600L, TimeUnit.SECONDS)
//                                                            .build();
//
//    @Override
//    public void action(LogTaxmanInfo taxmanInfo, ActionEvent event) {
//        ResourceLock lock = getUserAgentLock(taxmanInfo);
//        lock.lock();
//        try{
//            AbstractAction<?> action = taxmanAgentActionManager.getAction(event.getActionName());
//            if(null == action){
//                throw new ApplicationException("系统出错！");
//            }
//            action.act(event);
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    @Override
//    public TaxguideClientInfo taxguideClientInfoExtract(LogTaxmanInfo lti) {
//        TaxguideClientInfo tgclientinfo = new TaxguideClientInfo();
//        tgclientinfo.setLogTaxmanInfo(lti);
//        tgclientinfo.setWaitCount(getWaitingToDealInfo(lti.getAccId()));
//        if(StringUtils.isNotBlank(lti.getCurrentTgId())){
//            TaxguideRecord taxguideRecord = getTaxguideRecord(lti.getCurrentTgId());
//            tgclientinfo.setTaxguideRecord(taxguideRecord);
//            //意思一下搞个简单心跳
//            eventPublisher.publishEvent(new PollTimeEvent(taxguideRecord, LogTaxguidePollTime.Source.SWRY));
//        }
//        return tgclientinfo;
//    }
//
//    @Override
//    public LogTaxmanInfo getUserLogTaxmanInfo(String accId) {
//        LogTaxmanInfo lti = userRepository.findUserLogTaxmanInfoByAccId(accId);
//        if(null == lti){
//            lti = userRepository.createLogTaxmanInfo(accId);
//        }
//        return lti;
//    }
//
//    @Override
//    public PageResultVO<NsrAndTaskInfo> getCanDealTickets(String accId, int pageNo, int pageSize) {
//        PageResultVO<NsrAndTaskInfo> result = new PageResultVO<NsrAndTaskInfo>();
//        PageResultVO<String> pageResultVO = getCanDealCstIdsByAccId(accId,pageNo,pageSize);
//        List<String> cstIdList = pageResultVO.getResult();
//        if(null != cstIdList && 0 < cstIdList.size()){
//            List<NsrAndTaskInfo> resultList = userRepository.getNsrAndTaskInfoListByCstIds(cstIdList);
//            if(0 < resultList.size()) {
//                result.setPageNo(pageNo);
//                result.setPageSize(pageSize);
//                result.setTotal(pageResultVO.getTotal());
//                result.setResult(resultList);
//            }
//        }
//        return result;
//    }
//
//    private PageResultVO<String> getCanDealCstIdsByAccId(String accId, int pageNo, int pageSize){
//        CsServerParams csServerParams =  CsServerParams
//                                            .newBuilder()
//                                            .accId(accId)
//                                            .pageNo(pageNo)
//                                            .pageSize(pageSize)
//                                            .queueChannelId(TAXGUIDE_CHANNEL)
//                                            .build();
//        GsonObjResult<PageResultVO<String>> csResult = csFeignClient.getCanDealCstIdsByAccId(csServerParams);
//        PageResultVO<String> pageResultVO = ResultUtil.getResultData(csResult);
//        return pageResultVO;
//    };
//
//    private TaxguideRecord getTaxguideRecord(String tgId){
//        TaxguideRecord taxguideRecord = taxguideRepository.findTaxguideRecordById(tgId);
//        TaxguideRecordInfo taxguideRecordInfo = taxguideRepository
//                .findTaxguideRecordInfoById(taxguideRecord.getCurrentTgInfoId());
//        taxguideRecord.setCurrentTaxguideRecordInfo(taxguideRecordInfo);
//        return taxguideRecord;
//    }
//
//    private int getWaitingToDealInfo(String accId){
//        int waitCount = 0;
//        CsServerParams csServerParams =  CsServerParams.newBuilder().accId(accId).build();
//        GsonObjResult<Map<String, Integer>> csResult = csFeignClient.getWaitingToDealInfo(csServerParams);
//        Map<String, Integer> map = ResultUtil.getResultData(csResult);
//        Integer count = map.get(TAXGUIDE_CHANNEL_CODE);
//        if(null != count){
//            waitCount = count;
//        }
//        return waitCount;
//    }
//
//    private ResourceLock getUserAgentLock(LogTaxmanInfo ti){
//        ResourceLock userAgentLock = taxmanLockCache.getIfPresent(ti.getAccId());
//        if(userAgentLock == null){
//            synchronized(taxmanLockCache){
//                userAgentLock = taxmanLockCache.getIfPresent(ti.getAccId());
//                if(userAgentLock == null){
//                    userAgentLock = lockFactory.newLock("TAXMAN_" + ti.getAccId());
//                    taxmanLockCache.put(ti.getAccId(), userAgentLock);
//                }
//            }
//        }
//        return userAgentLock;
//    }
}
