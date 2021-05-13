package com.raymon.taxguide.model;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@ApiModel("导税记录附录")
@TableName("T_TAXGUIDE_RECORD_INFO")
@Data
@KeySequence(value = "S_TG_INFO_ID")
@Alias("taxguideRecordInfo")
public class TaxguideRecordInfo implements Serializable {

    private static final long serialVersionUID = 4240583426511868208L;

    public TaxguideRecordInfo(){};

    public TaxguideRecordInfo(String tgId,String csTicketId){
        this.tgId = tgId;
        this.csTicketId = csTicketId;
    };

    @ApiModelProperty("主键")
    @TableId(value = "TG_INFO_ID" , type = IdType.INPUT)
    private long tgInfoId;

    @ApiModelProperty("主记录主键")
    @TableField(value = "TG_ID")
    private String tgId;

    @ApiModelProperty("导税状态：0 等待叫号、1 叫号中、2 纳税人端就绪、3 导税中、4 导税完成、5 弃号")
    @TableField(value = "STATE")
    private int state;

    @ApiModelProperty("导税员ID")
    @TableField(value = "ACC_ID")
    private String accId;

    @ApiModelProperty("创建时间")
    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @ApiModelProperty("呼叫时间")
    @TableField(value = "CALL_TIME")
    private Date callTime;

    @ApiModelProperty("开始时间")
    @TableField(value = "START_TIME")
    private Date startTime;

    @ApiModelProperty("完成时间")
    @TableField(value = "FINISH_TIME")
    private Date finishTime;

    @ApiModelProperty("纳税人反馈意见")
    @TableField(value = "REMARK")
    private String remark;

    @ApiModelProperty("排队ID")
    @TableField(value = "CS_TICKET_ID")
    private String csTicketId;

    @TableField(exist = false)
    private String accCode;

    @TableField(exist = false)
    private String empNumber;
}
