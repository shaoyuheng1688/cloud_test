package com.raymon.frame.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdInfoVo implements Serializable {

    private static final long serialVersionUID = 3312131639402083353L;

    private String adCode;

    private String adName;

    private String parentAdCode;
}
