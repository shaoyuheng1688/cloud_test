package com.raymon.taxguide.repository;

import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.model.WorkCalendar;
import com.raymon.taxguide.pojo.NsrAndTaskInfo;

import java.util.Date;
import java.util.List;

public interface UserRepository {

	LogTaxmanInfo findLogTaxmanInfoById(long id);

	LogTaxmanInfo updateLogTaxmanInfo(LogTaxmanInfo logTaxmanInfo);

	LogTaxmanInfo createLogTaxmanInfo(String accId);

	boolean deleteLogTaxmanInfo(LogTaxmanInfo logTaxmanInfo);

	LogTaxmanInfo findUserLogTaxmanInfoByAccId(String accId);

	List<NsrAndTaskInfo> getNsrAndTaskInfoListByCstIds(List<String> cstIds);

}

