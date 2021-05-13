package com.raymon.taxguide.repository.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.raymon.taxguide.mapper.ThemeMapper;
import com.raymon.taxguide.mapper.WorkCalendarMapper;
import com.raymon.taxguide.model.Theme;
import com.raymon.taxguide.model.WorkCalendar;
import com.raymon.taxguide.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component("businessRepository")
public class BusinessRepositoryImp implements BusinessRepository {

	@Autowired
	private ThemeMapper themeMapper;
	@Autowired
	private WorkCalendarMapper workCalendarMapper;


	@Override
	public Theme findThemeByZtbm(String ztbm) {
		return themeMapper.selectById(ztbm);
	}

	@Override
	public WorkCalendar findWorkDayByDate(Date dateValue) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateValue);
		QueryWrapper<WorkCalendar> wrapper = new QueryWrapper<WorkCalendar>();
		wrapper.eq("CAL_YEAR",calendar.get(Calendar.YEAR))
				.eq("CAL_MONTH",calendar.get(Calendar.MONTH) + 1)
				.eq("CAL_DATE",calendar.get(Calendar.DATE));
		return workCalendarMapper.selectOne(wrapper);
	}
}
