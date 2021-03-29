

package com.raymon.taxguide.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

import lombok.Data;
import org.guzz.annotations.Table;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@javax.persistence.Entity
@org.guzz.annotations.Entity(businessName="workCalendar")
@Table(name="T_WORK_CALENDAR")
public class WorkCalendar implements Serializable {

	private static final long serialVersionUID = -6002856634278614159L;

	@javax.persistence.Id
	@XmlAttribute
	@Column(name="ID")
	private long id;
	
	@XmlAttribute
	@Column(name="DATE_VALUE")
	private Date dateValue;
	
	@XmlAttribute
	@Column(name="CAL_YEAR")
	private long calYear;
	
	@XmlAttribute
	@Column(name="CAL_MONTH")
	private long calMonth;
	
	@XmlAttribute
	@Column(name="CAL_DATE")
	private long calDate;
	
	@XmlAttribute
	@Column(name="CAL_DAY")
	private long calDay;
	
	@XmlAttribute
	@Column(name="IS_WORKDAY")
	private int isWorkday;

	@XmlAttribute
	@Column(name="TIME_PERIOD")
	private int timePeriod;
}