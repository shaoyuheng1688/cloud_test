package com.raymon.taxguide.vo;

import java.util.Date;

public class IgBookSessionVo {

    private String ywmc;

    private String address;

    private String Dwmc;

    private int hallType;

    private String formTotalId;

    private String newTimeSliceId;    // 兼容非long类型的timeSliceId

    private String newDwid;            // 兼容非long类型的dwid

    private Long businessId;

    private long ywid;//分类业务ID

    private String nsrsbh;

    private long operatorId;

    private long sessionId;

    private long transactionId;

    private long dwid;

    private long state;

    private Date createTime;

    private Date modifiedTime;

    private Date bookingDate;

    private long schDateId;

    private long timeSliceId;

    private Date startTime;

    private Date endTime;

    private Date reScheduleTime;

    private String xm;

    private String sfzh;

    private String zjlx;

    private String mobilePhone;

    private String ztbm;

    private long priority;

    private long channelId;

    private long queueChanenelId;

    private long ywlb;

    private String swjgDm;

    private long households;

    private long itemId;

    private Date assignFinishTime;

    private IgBookSessionEformExtra igBookSessionEformExtra;

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDwmc() {
        return Dwmc;
    }

    public void setDwmc(String dwmc) {
        Dwmc = dwmc;
    }

    public int getHallType() {
        return hallType;
    }

    public void setHallType(int hallType) {
        this.hallType = hallType;
    }

    public String getFormTotalId() {
        return formTotalId;
    }

    public void setFormTotalId(String formTotalId) {
        this.formTotalId = formTotalId;
    }

    public String getNewTimeSliceId() {
        return newTimeSliceId;
    }

    public void setNewTimeSliceId(String newTimeSliceId) {
        this.newTimeSliceId = newTimeSliceId;
    }

    public String getNewDwid() {
        return newDwid;
    }

    public void setNewDwid(String newDwid) {
        this.newDwid = newDwid;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public long getYwid() {
        return ywid;
    }

    public void setYwid(long ywid) {
        this.ywid = ywid;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getDwid() {
        return dwid;
    }

    public void setDwid(long dwid) {
        this.dwid = dwid;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public long getSchDateId() {
        return schDateId;
    }

    public void setSchDateId(long schDateId) {
        this.schDateId = schDateId;
    }

    public long getTimeSliceId() {
        return timeSliceId;
    }

    public void setTimeSliceId(long timeSliceId) {
        this.timeSliceId = timeSliceId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getReScheduleTime() {
        return reScheduleTime;
    }

    public void setReScheduleTime(Date reScheduleTime) {
        this.reScheduleTime = reScheduleTime;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getZtbm() {
        return ztbm;
    }

    public void setZtbm(String ztbm) {
        this.ztbm = ztbm;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public long getQueueChanenelId() {
        return queueChanenelId;
    }

    public void setQueueChanenelId(long queueChanenelId) {
        this.queueChanenelId = queueChanenelId;
    }

    public long getYwlb() {
        return ywlb;
    }

    public void setYwlb(long ywlb) {
        this.ywlb = ywlb;
    }

    public String getSwjgDm() {
        return swjgDm;
    }

    public void setSwjgDm(String swjgDm) {
        this.swjgDm = swjgDm;
    }

    public long getHouseholds() {
        return households;
    }

    public void setHouseholds(long households) {
        this.households = households;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public Date getAssignFinishTime() {
        return assignFinishTime;
    }

    public void setAssignFinishTime(Date assignFinishTime) {
        this.assignFinishTime = assignFinishTime;
    }

    public IgBookSessionEformExtra getIgBookSessionEformExtra() {
        return igBookSessionEformExtra;
    }

    public void setIgBookSessionEformExtra(IgBookSessionEformExtra igBookSessionEformExtra) {
        this.igBookSessionEformExtra = igBookSessionEformExtra;
    }
}
