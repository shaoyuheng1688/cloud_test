package com.raymon.taxguide.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 纳税人企业信息VO
 */
@Data
public class NsrqyxxVO implements Serializable {
    private static final long serialVersionUID = -7614993739703538751L;
    private String djxh;//登记序号
    private String nsrsbh;//纳税人识别号
    private String nsrmc;//纳税人名称
    private String jbrSfzjhm;//经办人身份证件号码
    private String jbrSf;//经办人身份
    private String yxbz;//有效标志,Y有效，N无效
    private String swjgDm;//税务机关代码
    private String swjgMc;//税务机关名称
    private String zjhm;
    private String zjlx;
    private String gsdjxh;
    private String gsnsrsbh;
    private String gsnsrmc;
    private String shxydm;
    private String zgswjg_dm;
    private String zgswjg;

    /**
     * 身份代码:01:法人,02:财务,03:办税员
     */
    private String sfdm;
}

