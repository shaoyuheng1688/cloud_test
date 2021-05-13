package com.raymon.taxguide.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * 主题信息表
 */
@TableName("T_THEME")
@Data
public class Theme implements Serializable {

    private static final long serialVersionUID = 8126159423953311657L;
    
    @TableId("ZTBM")
    private String ztbm;

    @XmlAttribute
    @TableField(value = "ZTMC")
    private String ztmc;

    @XmlAttribute
    @TableField(value = "SYNSRLX")
    private String synsrlx;

    @XmlAttribute
    @TableField(value = "SJZTBM")
    private String sjztbm;

    /**
     * 个人可办理企业主题
     */
    @XmlAttribute
    @TableField(value = "GRKBQYZT")
    private String grkbqyzt;

    /**
     * 接口查询标志，0为不需要查询，1为需要查询未办结（通用标志，用于标志主题需要查询什么接口）
     */
    @XmlAttribute
    @TableField(value = "JKCXBZ")
    private String jkcxbz;

    @XmlAttribute
    @TableField(value = "DSDM")
    private String dsdm;

    @XmlAttribute
    @TableField(value = "ZTSXGS")
    private String ztsxgs;
}
