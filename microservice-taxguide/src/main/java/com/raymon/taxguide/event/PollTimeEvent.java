package com.raymon.taxguide.event;
import org.springframework.context.ApplicationEvent;

public class PollTimeEvent extends ApplicationEvent {

    private static final long serialVersionUID = 2029246014144034568L;

    private int sourceType;

    public PollTimeEvent(Object source, int sourceType){
        super(source);
        this.sourceType = sourceType;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }
}
