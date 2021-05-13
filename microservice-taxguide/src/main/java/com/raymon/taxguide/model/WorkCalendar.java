

package com.raymon.taxguide.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@TableName("T_WORK_CALENDAR")
public class WorkCalendar implements Serializable {

	private static final long serialVersionUID = -6002856634278614159L;
	
	@XmlAttribute
	@TableId("ID")
	private long id;
	
	@XmlAttribute
	@TableField(value ="DATE_VALUE")
	private Date dateValue;
	
	@XmlAttribute
	@TableField(value ="CAL_YEAR")
	private long calYear;
	
	@XmlAttribute
	@TableField(value ="CAL_MONTH")
	private long calMonth;
	
	@XmlAttribute
	@TableField(value ="CAL_DATE")
	private long calDate;
	
	@XmlAttribute
	@TableField(value ="CAL_DAY")
	private long calDay;
	
	@XmlAttribute
	@TableField(value ="IS_WORKDAY")
	private int isWorkday;

	@XmlAttribute
	@TableField(value ="TIME_PERIOD")
	private int timePeriod;
}