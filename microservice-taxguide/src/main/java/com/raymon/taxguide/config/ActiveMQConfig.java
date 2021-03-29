package com.raymon.taxguide.config;


import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Topic;


@Configuration
public class ActiveMQConfig {

    @Value("${spring.activemq.topic-name}")
    private String topicName;

    @Bean(name = "topic")
    public Topic topic() {
        return new ActiveMQTopic(topicName);
    }

}
