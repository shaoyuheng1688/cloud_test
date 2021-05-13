package com.raymon.taxguide.listener;

import com.raymon.taxguide.event.CallMessageEvent;
import com.raymon.taxguide.jms.TaxguideMessageProducer;
import com.raymon.taxguide.model.TaxguideRecord;
import com.raymon.taxguide.model.Theme;
import com.raymon.taxguide.repository.BusinessRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
//@Component
@Slf4j
public class MessageEventListener implements ApplicationListener<CallMessageEvent> {

//    @Autowired
//    private TaxguideMessageProducer taxguideMessageProducer;
//    @Autowired
//    private BusinessRepository businessRepository;

    @Async
    @Override
    public void onApplicationEvent(CallMessageEvent event) {
//        try{
//            TaxguideRecord taxguideRecord = (TaxguideRecord)event.getSource();
//            Theme theme = businessRepository.findThemeByZtbm(taxguideRecord.getZtbm());
//            taxguideRecord.setZtmc(theme.getZtmc());
//            taxguideMessageProducer.sendTopic(taxguideRecord);
//        }catch (Exception e){
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
    }

}