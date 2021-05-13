package com.raymon.taxguide.jms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Topic;

//@Component
@Slf4j
public class TaxguideMessageProducer {

//    @Autowired
//    private JmsMessagingTemplate jmsMessagingTemplate;
//
//    @Resource
//    private Topic topic;
//
//    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//
//    public void sendTopic(Object msg) {
//        String message = gson.toJson(msg);
//        log.info("发送Topic消息内容 :"+ message);
//        this.jmsMessagingTemplate.convertAndSend(this.topic, message);
//    }
}
