package com.raymon.taxguide.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.guzz.annotations.GenericGenerator;
import org.guzz.annotations.Parameter;
import org.guzz.annotations.Table;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@ApiModel("导税记录附录")
@javax.persistence.Entity
@org.guzz.annotations.Entity(businessName="taxguideRecordInfo")
@Table(name="T_TAXGUIDE_RECORD_INFO")
@Data
public class TaxguideRecordInfo implements Serializable {

    private static final long serialVersionUID = 4240583426511868208L;

    public TaxguideRecordInfo(){};

    public TaxguideRecordInfo(String tgId,String csTicketId){
        this.tgId = tgId;
        this.csTicketId = csTicketId;
    };

    @ApiModelProperty("主键")
    @javax.persistence.Id
    @org.guzz.annotations.GenericGenerator(name = "SEQ_S_TG_INFO_ID", parameters = {
            @Parameter(name = "useAssignedCond", value = "#value>0"),
            @Parameter(name = "sequence", value = "S_TG_INFO_ID")}, strategy = "sequence")
    @javax.persistence.GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_S_TG_INFO_ID")
    @Column(name = "TG_INFO_ID")
    private long tgInfoId;

    @ApiModelProperty("主记录主键")
    @Column(name = "TG_ID")
    private String tgId;

    @ApiModelProperty("导税状态：0 等待叫号、1 叫号中、2 纳税人端就绪、3 导税中、4 导税完成、5 弃号")
    @Column(name = "STATE")
    private int state;

    @ApiModelProperty("导税员ID")
    @Column(name = "ACC_ID")
    private String accId;

    @ApiModelProperty("创建时间")
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @ApiModelProperty("呼叫时间")
    @Column(name = "CALL_TIME")
    private Date callTime;

    @ApiModelProperty("开始时间")
    @Column(name = "START_TIME")
    private Date startTime;

    @ApiModelProperty("完成时间")
    @Column(name = "FINISH_TIME")
    private Date finishTime;

    @ApiModelProperty("纳税人反馈意见")
    @Column(name = "REMARK")
    private String remark;

    @ApiModelProperty("排队ID")
    @Column(name = "CS_TICKET_ID")
    private String csTicketId;

    @Transient
    private String accCode;

    @Transient
    private String empNumber;
}
