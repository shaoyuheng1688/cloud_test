package com.raymon.taxguide.config;


import com.raymon.taxguide.event.PollTimeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@Slf4j
public class EventListenerConfig {

    @EventListener
    public void handleCustomEvent(PollTimeEvent pollTimeEvent) {
        //监听 CustomEvent事件
        log.info("监听到pollTimeEvent事件:", pollTimeEvent.getTimestamp());
    }

}
