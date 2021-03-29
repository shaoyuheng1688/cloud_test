package com.raymon.taxguide.dao.imp;


import com.raymon.taxguide.dao.BusinessDao;
import com.raymon.taxguide.model.TaxguideRecord;
import com.raymon.taxguide.model.Theme;
import com.raymon.taxguide.model.WorkCalendar;
import org.guzz.dao.GuzzBaseDao;
import org.guzz.orm.se.SearchExpression;
import org.guzz.orm.se.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("businessDao")
public class BusinessDaoImp implements BusinessDao {

	//这里是不规范操作日后优化TODO

	@Autowired
	private GuzzBaseDao guzzBaseDao;

	@Override
	public Theme findThemeByZtbm(String ztbm) {
		SearchExpression se = SearchExpression.forClass(Theme.class);
		se.and(Terms.eq("ztbm", ztbm));
		se.setPageSize(1);
		return (Theme)guzzBaseDao.findObject(se);
	}

	@Override
	public WorkCalendar findWorkDayByDate(Date dateValue) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateValue);
		SearchExpression se = SearchExpression.forClass(WorkCalendar.class);
		se.and(Terms.eq("calYear", calendar.get(Calendar.YEAR)));
		se.and(Terms.eq("calMonth", calendar.get(Calendar.MONTH) + 1));
		se.and(Terms.eq("calDate", calendar.get(Calendar.DATE)));

		return (WorkCalendar)guzzBaseDao.findObject(se);
	}
}
