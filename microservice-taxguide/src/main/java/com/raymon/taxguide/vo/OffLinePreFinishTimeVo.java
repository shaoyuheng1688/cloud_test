package com.raymon.taxguide.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OffLinePreFinishTimeVo {

    //返回状态，0.无法离线办理 1.正常 2.主题未配置 3.离线任务数已满 4.系统异常
    public static class Code {
        //无法离线办理
        public static final int NOT_DEAL = 0;
        //正常
        public static final int OK = 1;
        //主题未配置
        public static final int UN_SETTING = 2;
        //离线任务数已满
        public static final int DEAL_FULL = 3;
        //系统异常
        public static final int ERROR = 4;
    }

    private long muId;

    private String ztbm;

    private Date preStartTime;

    private Date preEndTime;

    private Date bookSliceStartTime;

    private Date bookSliceEndTime;

    private long code;

    private Date preFinishTime;

    private List<Date> assignDates;

    private long preWorkDay;
}
