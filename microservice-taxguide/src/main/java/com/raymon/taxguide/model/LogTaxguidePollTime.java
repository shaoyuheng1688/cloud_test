package com.raymon.taxguide.model;

import lombok.Data;
import org.guzz.annotations.Parameter;
import org.guzz.annotations.Table;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Date;

@javax.persistence.Entity
@org.guzz.annotations.Entity(businessName="logTaxguidePollTime")
@Table(name="T_LOG_TAXGUIDE_POLL_TIME")
@Data
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

    @javax.persistence.Id
    @org.guzz.annotations.GenericGenerator(name = "SEQ_LOG_TAXGUIDE_PT_ID", parameters = {
            @Parameter(name = "useAssignedCond", value = "#value>0"),
            @Parameter(name = "sequence", value = "S_LOG_TAXGUIDE_PT_ID")}, strategy = "sequence")
    @javax.persistence.GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LOG_TAXGUIDE_PT_ID")
    @Column(name = "PT_ID")
    private long ptId;

    @Column(name = "TG_ID")
    private String tgId;

    @Column(name = "SOURCE")
    private int source;

    @Column(name = "SOURCE_ID")
    private String sourceId;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "POLL_TIME")
    private Date pollTime;
}
