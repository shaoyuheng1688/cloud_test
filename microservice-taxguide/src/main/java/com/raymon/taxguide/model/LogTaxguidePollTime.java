package com.raymon.taxguide.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@TableName("T_LOG_TAXGUIDE_POLL_TIME")
@Data
@KeySequence(value = "S_LOG_TAXGUIDE_PT_ID")
public class LogTaxguidePollTime implements Serializable {

    private static final long serialVersionUID = 7777364359807983577L;

    public LogTaxguidePollTime(){}

    public LogTaxguidePollTime(String tgId, int source, String sourceId){
        this.tgId = tgId;
        this.source = source;
        this.sourceId = sourceId;
    }

    public static class Source {

        /**
         *来源纳税人端
         */
        public static final int NSR = 1;
        /**
         *来税务人员端
         */
        public static final int SWRY = 2;
    }

    @TableId(value = "PT_ID" , type = IdType.INPUT)
    private long ptId;

    @TableField(value = "TG_ID")
    private String tgId;

    @TableField(value = "SOURCE")
    private int source;

    @TableField(value = "SOURCE_ID")
    private String sourceId;

    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @TableField(value = "POLL_TIME")
    private Date pollTime;
}
