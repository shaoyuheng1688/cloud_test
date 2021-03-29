package com.raymon.taxguide.pojo.eform;

import lombok.Data;

import java.io.Serializable;

@Data
public class EformServerParams implements Serializable {

    private static final long serialVersionUID = 6110912177371118703L;

    private String recordId;

    private String muId;
}
