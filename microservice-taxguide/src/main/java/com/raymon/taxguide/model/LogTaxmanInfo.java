package com.raymon.taxguide.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.guzz.annotations.Parameter;
import org.guzz.annotations.Table;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@ApiModel("导税员信息")
@javax.persistence.Entity
@org.guzz.annotations.Entity(businessName="taxmanInfo")
@Table(name="T_LOG_TAXMAN_INFO")
@Data
public class LogTaxmanInfo implements Serializable {

    private static final long serialVersionUID = -1583611829230809242L;

    public LogTaxmanInfo(){}

    public LogTaxmanInfo(String accId){
        this.accId = accId;
    }

    @ApiModelProperty("主键")
    @javax.persistence.Id
    @org.guzz.annotations.GenericGenerator(name = "SEQ_TG_TI_ID", parameters = {
            @Parameter(name = "useAssignedCond", value = "#value>0"),
            @Parameter(name = "sequence", value = "S_TG_TI_ID")}, strategy = "sequence")
    @javax.persistence.GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_TG_TI_ID")
    @Column(name = "TI_ID")
    private long tiId;

    @ApiModelProperty("导税员ID")
    @Column(name = "ACC_ID")
    private String accId;

    @ApiModelProperty("税务人员当前动作：IDLE 空闲、CALLING 呼叫中、DEALING 办理中")
    @Column(name = "ACTION")
    private String action;

    @ApiModelProperty("创建时间")
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @ApiModelProperty("当前办理的导税记录主键")
    @Column(name = "CURRENT_TG_ID")
    private String currentTgId;
}
