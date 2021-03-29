package com.raymon.frame.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Calendar;

@Data
public class AccessRecord implements Serializable {


	private static final long serialVersionUID = 316839003717708381L;
	private String mobilePhone;
	private String ip;
	private long lastAccessTime;//最近一次访问的时间
	private long accessTimes;//访问次数
	
	public AccessRecord(String mobilePhone, String ip) {
		this.mobilePhone = mobilePhone;
		this.ip = ip;
		this.lastAccessTime = Calendar.getInstance().getTimeInMillis();
		this.accessTimes = 0;
	}
}
