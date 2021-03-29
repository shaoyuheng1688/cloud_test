package com.raymon.frame.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 纳税人身份信息VO
 */
@Data
public class NsrsfxxVO implements Serializable {
    private static final long serialVersionUID = 5044288591330656597L;
    private String zjhm;//证件号码
    private String zjlx;//证件类型
    private String xm;//姓名
    private String xb;//性别
    private String sjhm;//手机号码
    private String rxDownloadPath;//人像图片路径
    private String zjDownloadPath;//证件正面图片路径
}

