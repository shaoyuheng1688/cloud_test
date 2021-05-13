package com.raymon.taxguide.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class IGItemConfigVo implements Serializable {

    private static final long serialVersionUID = 6197693034934209376L;

    private long icId;

    private String key;

    private String itemId;

    private long muId;

    private int value;

    private String swjgDm;

    public IGItemConfigVo() {
    }

}
