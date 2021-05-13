package com.raymon.taxguide.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class ManageUnitVo implements Serializable {

	private static final long serialVersionUID = 8509417209064401491L;

	private Long muId;

	private String muName;

	private Long parentId;

	private long disabled;

	private String muCode;

	private String parentMuCode;

	private long rootMuId;

	private long rank;
	
}
