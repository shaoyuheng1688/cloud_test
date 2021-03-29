package com.raymon.taxguide.listener;

import com.raymon.taxguide.event.PollTimeEvent;
import com.raymon.taxguide.model.LogTaxguidePollTime;
import com.raymon.taxguide.model.TaxguideRecord;
import com.raymon.taxguide.repository.TaxguideRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
@Slf4j
public class PollTimeListener implements ApplicationListener<PollTimeEvent> {

    @Autowired
    private TaxguideRepository taxguideRepository;

    @Async
    @Override
    public void onApplicationEvent(PollTimeEvent event) {
        try{
            TaxguideRecord taxguideRecord = (TaxguideRecord)event.getSource();
            int sourceType = event.getSourceType();
            LogTaxguidePollTime logTaxguidePollTime =
                    taxguideRepository.findLogTaxguidePollTimeByTgIdAndSource(taxguideRecord.getTgId(),sourceType);
            if(null == logTaxguidePollTime){
                createLogTaxguidePollTime(taxguideRecord,sourceType);
            }else{
                updateLogTaxguidePollTime(logTaxguidePollTime);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private void createLogTaxguidePollTime(TaxguideRecord taxguideRecord,int sourceType){
        String sourceId = taxguideRecord.getIdentityNumber();
        if(sourceType == LogTaxguidePollTime.Source.SWRY){
            sourceId = taxguideRecord.getCurrentTaxguideRecordInfo().getAccId();
        }
        LogTaxguidePollTime logTaxguidePollTime =
                new LogTaxguidePollTime(taxguideRecord.getTgId(),sourceType,sourceId);
        taxguideRepository.saveLogTaxguidePollTime(logTaxguidePollTime);
        log.info("PT_ID:"+ logTaxguidePollTime.getPtId() +"_upData_Poll_nowTime:" + logTaxguidePollTime.getPollTime());
    }

    private void updateLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime){
        Date nowTime = Calendar.getInstance().getTime();
        Date pollTime = logTaxguidePollTime.getPollTime();
        //两分钟更新一次最后轮询时间
        pollTime = DateUtils.addMinutes(pollTime, 2);
        if(nowTime.before(pollTime)){
            logTaxguidePollTime.setPollTime(nowTime);
            taxguideRepository.updateLogTaxguidePollTime(logTaxguidePollTime);
            log.info("PT_ID:"+ logTaxguidePollTime.getPtId() +"_upData_Poll_nowTime:" + pollTime);
        }
    }
}