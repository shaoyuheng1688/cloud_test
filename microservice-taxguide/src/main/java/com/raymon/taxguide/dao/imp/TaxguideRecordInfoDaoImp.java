package com.raymon.taxguide.dao.imp;

import com.raymon.taxguide.dao.TaxguideRecordInfoDao;
import com.raymon.taxguide.model.TaxguideRecordInfo;
import org.guzz.dao.GuzzBaseDao;
import org.guzz.orm.se.SearchExpression;
import org.guzz.orm.se.Terms;
import org.guzz.transaction.ReadonlyTranSession;
import org.guzz.transaction.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("taxguideRecordInfoDao")
public class TaxguideRecordInfoDaoImp implements TaxguideRecordInfoDao {

	@Autowired
	private GuzzBaseDao guzzBaseDao;

	@Override
	public TaxguideRecordInfo findTaxguideRecordInfoById(long id) {
		SearchExpression se = SearchExpression.forClass(TaxguideRecordInfo.class);
		se.and(Terms.eq("tgInfoId", id));
		se.setPageSize(Integer.MAX_VALUE);
		return (TaxguideRecordInfo)guzzBaseDao.findObject(se);
	}

	@Override
	public TaxguideRecordInfo updateTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo) {
		guzzBaseDao.getWriteTemplate().update(taxguideRecordInfo);
		return taxguideRecordInfo;
	}

	@Override
	public TaxguideRecordInfo saveTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo) {
		Date createDate = Calendar.getInstance().getTime();
		taxguideRecordInfo.setCreateTime(createDate);
		long id = (long) guzzBaseDao.insert(taxguideRecordInfo);
		taxguideRecordInfo.setTgInfoId(id);
		return taxguideRecordInfo;
	}

	@Override
	public boolean deleteTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo) {
		return guzzBaseDao.getWriteTemplate().delete(taxguideRecordInfo);
	}

	@Override
	public List<TaxguideRecordInfo> getTaxGuideRecordInfosByBussId(String bussId) {
		Map<String, Object> params = new HashMap<String, Object>();
		TransactionManager tm = guzzBaseDao.getTransactionManager();
		ReadonlyTranSession session = tm.openDelayReadTran();
		params.put("bussId", bussId);
		List<TaxguideRecordInfo> list = null;
		try {
			list = session.list("selectTaxGuideRecordInfosByBussId", params);
		} finally {
			session.close();
		}
		return list;
	}
}
