package com.raymon.taxguide.util;


public enum CacheType {
    /**
     * 导税任务记录
     */
    tgRecord(900,":"),

    /**
     * 导税任务明细记录
     */
    tgRecordInfo(900,":"),

    /**
     * 税务人员当前信息
     */
    taxmanInfo(3600,":");

    private int expires;

    private String prefix;

    CacheType(int expires,String prefix) {
        this.expires = expires;
        this.prefix = prefix;
    }

    public int getExpires() {
        return expires;
    }

    public String getPrefix() {
        return prefix;
    }
}
