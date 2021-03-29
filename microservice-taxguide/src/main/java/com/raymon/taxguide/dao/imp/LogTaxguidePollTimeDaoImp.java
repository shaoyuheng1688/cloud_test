package com.raymon.taxguide.dao.imp;

import com.raymon.taxguide.dao.LogTaxguidePollTimeDao;
import com.raymon.taxguide.model.LogTaxguidePollTime;
import org.guzz.dao.GuzzBaseDao;
import org.guzz.orm.se.SearchExpression;
import org.guzz.orm.se.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component("logTaxguidePollTimeDao")
public class LogTaxguidePollTimeDaoImp implements LogTaxguidePollTimeDao {

	@Autowired
	private GuzzBaseDao guzzBaseDao;

	@Override
	public LogTaxguidePollTime findLogTaxguidePollTimeById(Long id) {
		SearchExpression se = SearchExpression.forClass(LogTaxguidePollTime.class);
		se.and(Terms.eq("ptId", id));
		se.setPageSize(Integer.MAX_VALUE);
		return (LogTaxguidePollTime)guzzBaseDao.findObject(se);
	}

	@Override
	public LogTaxguidePollTime updateLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime) {
		guzzBaseDao.getWriteTemplate().update(logTaxguidePollTime);
		return logTaxguidePollTime;
	}

	@Override
	public LogTaxguidePollTime saveLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime) {
		Date createDate = Calendar.getInstance().getTime();
		logTaxguidePollTime.setPollTime(createDate);
		logTaxguidePollTime.setCreateTime(createDate);
		long id = (long) guzzBaseDao.insert(logTaxguidePollTime);
		logTaxguidePollTime.setPtId(id);
		return logTaxguidePollTime;
	}

	@Override
	public boolean deleteLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime) {
		return guzzBaseDao.getWriteTemplate().delete(logTaxguidePollTime);
	}

	@Override
	public LogTaxguidePollTime findLogTaxguidePollTimeByTgIdAndSource(String tgId, int source) {
		SearchExpression se = SearchExpression.forClass(LogTaxguidePollTime.class);
		se.and(Terms.eq("tgId", tgId));
		se.and(Terms.eq("source", source));
		se.setOrderBy("createTime desc");
		se.setPageSize(1);
		return (LogTaxguidePollTime)guzzBaseDao.findObject(se);
	}
}
