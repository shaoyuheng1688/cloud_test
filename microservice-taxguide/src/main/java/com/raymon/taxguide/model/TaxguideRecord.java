package com.raymon.taxguide.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@ApiModel("导税主记录")
@TableName("T_TAXGUIDE_RECORD")
@Data
public class TaxguideRecord implements Serializable {

    private static final long serialVersionUID = -9059099263549925995L;

    public TaxguideRecord(){}

    public TaxguideRecord(String bussId,String swjgDm,String ztbm,String identityNumber){
        this.bussId = bussId;
        this.swjgDm = swjgDm;
        this.ztbm = ztbm;
        this.identityNumber = identityNumber;
    }

    public static class State {
        /**
         * 等待叫号
         */
        public static final int WAIT = 0;
        /**
         * 叫号中
         */
        public static final int CALL = 1;
        /**
         * 纳税人端就绪
         */
        public static final int READY = 2;
        /**
         * 导税中
         */
        public static final int DEAL = 3;
        /**
         * 导税完成
         */
        public static final int FINISH = 4;
        /**
         * 弃号
         */
        public static final int GIVEUP = 5;
        /**
         * 异常
         */
        public static final int ERROR = 6;

    }

    @ApiModelProperty("主键")
    @TableId(value = "TG_ID" , type = IdType.ASSIGN_UUID)
    private String tgId;

    @ApiModelProperty("导税状态：0 等待叫号、1 叫号中、2 纳税人端就绪、3 导税中、4 导税完成、5 弃号")
    @TableField(value = "STATE")
    private int state;

    @ApiModelProperty("创建时间")
    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @ApiModelProperty("结束时间")
    @TableField(value = "END_TIME")
    private Date endTime;

    @ApiModelProperty("当前的导税副表ID")
    @TableField(value = "CURRENT_TG_INFO_ID")
    private long currentTgInfoId;

    @ApiModelProperty("业务ID，一般是填单记录ID")
    @TableField(value = "BUSSID")
    private String bussId;

    @ApiModelProperty("税务机关代码")
    @TableField(value = "SWJG_DM")
    private String swjgDm;

    @ApiModelProperty("主题编码")
    @TableField(value = "ZTBM")
    private String ztbm;

    @ApiModelProperty("主题名称")
    @TableField(exist = false)
    private String ztmc;

    @ApiModelProperty("纳税人身份证号")
    @TableField(value = "IDENTITY_NUMBER")
    private String identityNumber;

    @ApiModelProperty("当前副表详情信息")
    @TableField(exist = false)
    private TaxguideRecordInfo currentTaxguideRecordInfo;

    @ApiModelProperty("等候人数")
    @TableField(exist = false)
    private int waitCount;


}
