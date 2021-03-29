package com.raymon.taxguide.event;
import org.springframework.context.ApplicationEvent;

public class CallMessageEvent extends ApplicationEvent {

    private static final long serialVersionUID = 2185240780168540931L;

    public CallMessageEvent(Object source){
        super(source);
    }
}
