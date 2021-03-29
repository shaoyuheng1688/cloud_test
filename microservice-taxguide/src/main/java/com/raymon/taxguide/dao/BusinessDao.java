package com.raymon.taxguide.dao;

import com.raymon.taxguide.model.Theme;
import com.raymon.taxguide.model.WorkCalendar;

import java.util.Date;

public interface BusinessDao {

    Theme findThemeByZtbm(String ztbm);

    WorkCalendar findWorkDayByDate(Date dateValue);
}