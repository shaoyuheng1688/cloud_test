package com.raymon.taxguide.dao.imp;

import com.raymon.frame.utils.DateTimeUtils;
import com.raymon.taxguide.dao.LogTaxmanInfoDao;
import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.model.WorkCalendar;
import com.raymon.taxguide.pojo.NsrAndTaskInfo;
import org.guzz.dao.GuzzBaseDao;
import org.guzz.orm.se.SearchExpression;
import org.guzz.orm.se.Terms;
import org.guzz.transaction.ReadonlyTranSession;
import org.guzz.transaction.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("logTaxmanInfoDao")
public class LogTaxmanInfoDaoImp implements LogTaxmanInfoDao {

	@Autowired
	private GuzzBaseDao guzzBaseDao;

	@Override
	public LogTaxmanInfo findLogTaxmanInfoById(long id) {
		SearchExpression se = SearchExpression.forClass(LogTaxmanInfo.class);
		se.and(Terms.eq("tiId", id));
		se.setPageSize(Integer.MAX_VALUE);
		return (LogTaxmanInfo)guzzBaseDao.findObject(se);
	}

	@Override
	public LogTaxmanInfo updateLogTaxmanInfo(LogTaxmanInfo logTaxmanInfo) {
		guzzBaseDao.getWriteTemplate().update(logTaxmanInfo);
		return logTaxmanInfo;
	}

	@Override
	public LogTaxmanInfo saveLogTaxmanInfo(LogTaxmanInfo logTaxmanInfo) {
		Date createDate = Calendar.getInstance().getTime();
		logTaxmanInfo.setCreateTime(createDate);
		logTaxmanInfo.setAction("IDLE");
		long id = (long) guzzBaseDao.insert(logTaxmanInfo);
		logTaxmanInfo.setTiId(id);
		return logTaxmanInfo;
	}

	@Override
	public boolean deleteLogTaxmanInfo(LogTaxmanInfo LogTaxmanInfo) {
		return guzzBaseDao.getWriteTemplate().delete(LogTaxmanInfo);
	}

	@Override
	public LogTaxmanInfo findLogTaxmanInfoByAccId(String accId) {
		SearchExpression se = SearchExpression.forClass(LogTaxmanInfo.class);
		se.and(Terms.eq("accId", accId));
		se.and(Terms.bigger("createTime",DateTimeUtils.getTodayStartTime()));
		se.setOrderBy("createTime desc");
		se.setPageSize(1);
		return (LogTaxmanInfo)guzzBaseDao.findObject(se);
	}


	@Override
	public List<NsrAndTaskInfo> getNsrAndTaskInfoListByCstIds(List<String> cstIds) {
		//恶心的编码从这距sql开始
		Map<String, Object> params = new HashMap<String, Object>();
		TransactionManager tm = guzzBaseDao.getTransactionManager();
		ReadonlyTranSession session = tm.openDelayReadTran();
		params.put("cstIds",cstIds);
		List<NsrAndTaskInfo> list = null;
		try{
			list = session.list("selectNsrAndTaskInfoListByCstIds", params);
		} finally{
			session.close();
		}
		return list;
	}
}
