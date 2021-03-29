package com.raymon.taxguide.repository.imp;

import com.raymon.taxguide.dao.LogTaxguidePollTimeDao;
import com.raymon.taxguide.dao.TaxguideRecordDao;
import com.raymon.taxguide.dao.TaxguideRecordInfoDao;
import com.raymon.taxguide.model.LogTaxguidePollTime;
import com.raymon.taxguide.model.TaxguideRecord;
import com.raymon.taxguide.model.TaxguideRecordInfo;
import com.raymon.taxguide.repository.TaxguideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("taxguideRepository")
public class TaxguideRepositoryImp implements TaxguideRepository {

	@Autowired
	private TaxguideRecordDao taxguideRecordDao;

	@Autowired
	private TaxguideRecordInfoDao taxguideRecordInfoDao;

	@Autowired
	private LogTaxguidePollTimeDao logTaxguidePollTimeDao;

	@Override
	@Cacheable(value = "tgRecord",key = "#id",unless = "#result == null")
	public TaxguideRecord findTaxguideRecordById(String id) {
		return taxguideRecordDao.findTaxguideRecordById(id);
	}

	@Override
	@CacheEvict(value="tgRecord",key="#taxguideRecord.tgId")
	public TaxguideRecord updateTaxguideRecord(TaxguideRecord taxguideRecord) {
		return taxguideRecordDao.updateTaxguideRecord(taxguideRecord);
	}

	@Override
	@CacheEvict(value="tgRecord",key="#taxguideRecord.tgId")
	public TaxguideRecord saveTaxguideRecord(TaxguideRecord taxguideRecord) {
		return taxguideRecordDao.saveTaxguideRecord(taxguideRecord);
	}

	@Override
	@CacheEvict(value="tgRecord",key="#taxguideRecord.tgId")
	public boolean deleteTaxguideRecord(TaxguideRecord taxguideRecord) {
		return taxguideRecordDao.deleteTaxguideRecord(taxguideRecord);
	}

	@Override
	@Cacheable(value = "tgRecordInfo",key = "#id",unless = "#result == null")
	public TaxguideRecordInfo findTaxguideRecordInfoById(long id) {
		return taxguideRecordInfoDao.findTaxguideRecordInfoById(id);
	}

	@Override
	@CacheEvict(value="tgRecordInfo",key="#taxguideRecordInfo.tgInfoId")
	public TaxguideRecordInfo updateTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo) {
		return taxguideRecordInfoDao.updateTaxguideRecordInfo(taxguideRecordInfo);
	}

	@Override
	@CacheEvict(value="tgRecordInfo",key="#taxguideRecordInfo.tgInfoId")
	public TaxguideRecordInfo saveTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo) {
		return taxguideRecordInfoDao.saveTaxguideRecordInfo(taxguideRecordInfo);
	}

	@Override
	@CacheEvict(value="tgRecordInfo",key="#taxguideRecordInfo.tgInfoId")
	public boolean deleteTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo) {
		return taxguideRecordInfoDao.deleteTaxguideRecordInfo(taxguideRecordInfo);
	}

	@Override
	public LogTaxguidePollTime findLogTaxguidePollTimeById(Long id) {
		return logTaxguidePollTimeDao.findLogTaxguidePollTimeById(id);
	}

	@Override
	public LogTaxguidePollTime updateLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime) {
		return logTaxguidePollTimeDao.updateLogTaxguidePollTime(logTaxguidePollTime);
	}

	@Override
	public LogTaxguidePollTime saveLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime) {
		return logTaxguidePollTimeDao.saveLogTaxguidePollTime(logTaxguidePollTime);
	}

	@Override
	public boolean deleteLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime) {
		return logTaxguidePollTimeDao.deleteLogTaxguidePollTime(logTaxguidePollTime);
	}

	@Override
	public LogTaxguidePollTime findLogTaxguidePollTimeByTgIdAndSource(String tgId, int source) {
		return logTaxguidePollTimeDao.findLogTaxguidePollTimeByTgIdAndSource(tgId,source);
	}

	@Override
	public List<TaxguideRecordInfo> getTaxGuideRecordInfosByBussId(String bussId) {
		return taxguideRecordInfoDao.getTaxGuideRecordInfosByBussId(bussId);
	}

	@Override
	public TaxguideRecord findNotFinishTaxguideRecordByBussId(String bussId) {
		return taxguideRecordDao.findNotFinishTaxguideRecordByBussId(bussId);
	}
}
