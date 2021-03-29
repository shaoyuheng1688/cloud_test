package com.raymon.taxguide.repository.imp;

import com.raymon.taxguide.dao.BusinessDao;
import com.raymon.taxguide.model.Theme;
import com.raymon.taxguide.model.WorkCalendar;
import com.raymon.taxguide.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component("businessRepository")
public class BusinessRepositoryImp implements BusinessRepository {

	@Autowired
	private BusinessDao businessDao;


	@Override
	public Theme findThemeByZtbm(String ztbm) {
		return businessDao.findThemeByZtbm(ztbm);
	}

	@Override
	public WorkCalendar findWorkDayByDate(Date dateValue) {
		return businessDao.findWorkDayByDate(dateValue);
	}
}
