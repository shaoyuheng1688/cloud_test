package com.raymon.taxguide.dao;


import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.model.WorkCalendar;
import com.raymon.taxguide.pojo.NsrAndTaskInfo;

import java.util.Date;
import java.util.List;

public interface LogTaxmanInfoDao {

    LogTaxmanInfo findLogTaxmanInfoById(long id);

    LogTaxmanInfo updateLogTaxmanInfo(LogTaxmanInfo logTaxmanInfo);

    LogTaxmanInfo saveLogTaxmanInfo(LogTaxmanInfo logTaxmanInfo);

    boolean deleteLogTaxmanInfo(LogTaxmanInfo logTaxmanInfo);

    LogTaxmanInfo findLogTaxmanInfoByAccId(String accId);

    List<NsrAndTaskInfo> getNsrAndTaskInfoListByCstIds(List<String> cstIds);
}