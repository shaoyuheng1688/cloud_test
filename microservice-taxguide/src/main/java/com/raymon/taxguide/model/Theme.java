package com.raymon.taxguide.model;

import lombok.Data;
import org.guzz.annotations.GenericGenerator;
import org.guzz.annotations.Table;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * 主题信息表
 */
@javax.persistence.Entity
@org.guzz.annotations.Entity(businessName = "theme")
@Table(name = "T_THEME")
@Data
public class Theme implements Serializable {

    private static final long serialVersionUID = 8126159423953311657L;
    @javax.persistence.Id
    @Column(name = "ZTBM")
    private String ztbm;

    @XmlAttribute
    @Column(name = "ZTMC")
    private String ztmc;

    @XmlAttribute
    @Column(name = "SYNSRLX")
    private String synsrlx;

    @XmlAttribute
    @Column(name = "SJZTBM")
    private String sjztbm;

    /**
     * 个人可办理企业主题
     */
    @XmlAttribute
    @Column(name = "GRKBQYZT")
    private String grkbqyzt;

    /**
     * 接口查询标志，0为不需要查询，1为需要查询未办结（通用标志，用于标志主题需要查询什么接口）
     */
    @XmlAttribute
    @Column(name = "JKCXBZ")
    private String jkcxbz;

    @XmlAttribute
    @Column(name = "DSDM")
    private String dsdm;

    @XmlAttribute
    @Column(name = "ZTSXGS")
    private String ztsxgs;
}
