package com.raymon.frame.vo;

import java.util.Date;

public class IgBookSessionEformExtra {

    //预约记录ID
    private long sessionId;

    //最后修改时间
    private Date lastUploadTime;

    //预计完成时间
    private long preWorkDay;

    //是否失约激活 0:未激活 1:已激活
    private int isActivation;

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public Date getLastUploadTime() {
        return lastUploadTime;
    }

    public void setLastUploadTime(Date lastUploadTime) {
        this.lastUploadTime = lastUploadTime;
    }

    public long getPreWorkDay() {
        return preWorkDay;
    }

    public void setPreWorkDay(long preWorkDay) {
        this.preWorkDay = preWorkDay;
    }

    public int getIsActivation() {
        return isActivation;
    }

    public void setIsActivation(int isActivation) {
        this.isActivation = isActivation;
    }
}
