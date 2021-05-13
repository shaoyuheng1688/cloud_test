package com.raymon.taxguide.model;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@ApiModel("导税员信息")
@TableName("T_LOG_TAXMAN_INFO")
@Data
@KeySequence(value = "S_TG_TI_ID")
public class LogTaxmanInfo implements Serializable {

    private static final long serialVersionUID = -1583611829230809242L;

    public LogTaxmanInfo(){}

    public LogTaxmanInfo(String accId){
        this.accId = accId;
    }

    @ApiModelProperty("主键")
    @TableId(value = "TI_ID" , type = IdType.INPUT)
    private Long tiId;

    @ApiModelProperty("导税员ID")
    @TableField(value = "ACC_ID")
    private String accId;

    @ApiModelProperty("税务人员当前动作：IDLE 空闲、CALLING 呼叫中、DEALING 办理中")
    @TableField(value = "ACTION")
    private String action;

    @ApiModelProperty("创建时间")
    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @ApiModelProperty("当前办理的导税记录主键")
    @TableField(value = "CURRENT_TG_ID")
    private String currentTgId;
}
