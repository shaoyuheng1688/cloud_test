package com.raymon.taxguide.dao.imp;

import com.raymon.taxguide.dao.TaxguideRecordDao;
import com.raymon.taxguide.model.TaxguideRecord;
import org.guzz.dao.GuzzBaseDao;
import org.guzz.orm.se.SearchExpression;
import org.guzz.orm.se.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("taxguideRecordDao")
public class TaxguideRecordDaoImp implements TaxguideRecordDao {

	@Autowired
	private GuzzBaseDao guzzBaseDao;

	@Override
	public TaxguideRecord findTaxguideRecordById(String id) {
		SearchExpression se = SearchExpression.forClass(TaxguideRecord.class);
		se.and(Terms.eq("tgId", id));
		se.setPageSize(1);
		return (TaxguideRecord)guzzBaseDao.findObject(se);
	}

	@Override
	public TaxguideRecord updateTaxguideRecord(TaxguideRecord taxguideRecord) {
		guzzBaseDao.getWriteTemplate().update(taxguideRecord);
		return taxguideRecord;
	}

	@Override
	public TaxguideRecord saveTaxguideRecord(TaxguideRecord taxguideRecord) {
		Date createDate = Calendar.getInstance().getTime();
		taxguideRecord.setCreateTime(createDate);
		String id = (String) guzzBaseDao.insert(taxguideRecord);
		taxguideRecord.setTgId(id);
		return taxguideRecord;
	}

	@Override
	public boolean deleteTaxguideRecord(TaxguideRecord taxguideRecord) {
		return guzzBaseDao.getWriteTemplate().delete(taxguideRecord);
	}

	@Override
	public TaxguideRecord findNotFinishTaxguideRecordByBussId(String bussId) {
		SearchExpression se = SearchExpression.forClass(TaxguideRecord.class);
		se.and(Terms.eq("bussId", bussId));
		se.and(Terms.smaller("state", 4));
		se.setOrderBy("createTime desc");
		se.setPageSize(1);
		return (TaxguideRecord)guzzBaseDao.findObject(se);
	}
}
