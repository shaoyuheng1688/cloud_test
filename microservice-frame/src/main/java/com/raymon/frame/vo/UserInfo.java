package com.raymon.frame.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -8181370096235299512L;

    private NsrsfxxVO userDetails;//纳税人身份信息

    private List<NsrqyxxVO> userNsrs;//纳税人所有企业信息

    private NsrqyxxVO nsr;//当前登录企业

    String sfdm;//身份代码
}

