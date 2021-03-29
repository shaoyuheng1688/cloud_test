package com.raymon.taxguide.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.guzz.annotations.GenericGenerator;
import org.guzz.annotations.Table;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@ApiModel("导税主记录")
@javax.persistence.Entity
@org.guzz.annotations.Entity(businessName="taxguideRecord")
@Table(name="T_TAXGUIDE_RECORD")
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
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @Column(name = "TG_ID", length = 32)
    private String tgId;

    @ApiModelProperty("导税状态：0 等待叫号、1 叫号中、2 纳税人端就绪、3 导税中、4 导税完成、5 弃号")
    @Column(name = "STATE")
    private int state;

    @ApiModelProperty("创建时间")
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @ApiModelProperty("结束时间")
    @Column(name = "END_TIME")
    private Date endTime;

    @ApiModelProperty("当前的导税副表ID")
    @Column(name = "CURRENT_TG_INFO_ID")
    private long currentTgInfoId;

    @ApiModelProperty("业务ID，一般是填单记录ID")
    @Column(name = "BUSSID")
    private String bussId;

    @ApiModelProperty("税务机关代码")
    @Column(name = "SWJG_DM")
    private String swjgDm;

    @ApiModelProperty("主题编码")
    @Column(name = "ZTBM")
    private String ztbm;

    @ApiModelProperty("主题名称")
    @Transient
    private String ztmc;

    @ApiModelProperty("纳税人身份证号")
    @Column(name = "IDENTITY_NUMBER")
    private String identityNumber;

    @ApiModelProperty("当前副表详情信息")
    @Transient
    private TaxguideRecordInfo currentTaxguideRecordInfo;

    @ApiModelProperty("等候人数")
    @Transient
    private int waitCount;


}
