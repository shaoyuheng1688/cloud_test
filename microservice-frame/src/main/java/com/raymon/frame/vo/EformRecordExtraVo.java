package com.raymon.frame.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EformRecordExtraVo implements Serializable {

    private String recordId;

    private String onceNoticeId;

    private Long checkType;

    private Long queueChannelType;

    private Long assignSwjgType;
}
