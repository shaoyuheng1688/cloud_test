package com.raymon.taxguide.repository;

import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.model.Theme;
import com.raymon.taxguide.model.WorkCalendar;
import com.raymon.taxguide.pojo.NsrAndTaskInfo;

import java.util.Date;
import java.util.List;

public interface BusinessRepository {

	Theme findThemeByZtbm(String ztbm);

	WorkCalendar findWorkDayByDate(Date dateValue);
}

