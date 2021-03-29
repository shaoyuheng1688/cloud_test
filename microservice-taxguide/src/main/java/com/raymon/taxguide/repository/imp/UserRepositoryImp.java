package com.raymon.taxguide.repository.imp;

import com.raymon.taxguide.dao.LogTaxmanInfoDao;
import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.model.WorkCalendar;
import com.raymon.taxguide.pojo.NsrAndTaskInfo;
import com.raymon.taxguide.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component("userRepository")
public class UserRepositoryImp implements UserRepository {

	@Autowired
	private LogTaxmanInfoDao logTaxmanInfoDao;

	@Override
	public LogTaxmanInfo findLogTaxmanInfoById(long id) {
		return logTaxmanInfoDao.findLogTaxmanInfoById(id);
	}

	@Override
	@CacheEvict(value="taxmanInfo",key="#logTaxmanInfo.accId")
	public LogTaxmanInfo updateLogTaxmanInfo(LogTaxmanInfo logTaxmanInfo) {
		return logTaxmanInfoDao.updateLogTaxmanInfo(logTaxmanInfo);
	}

	@Override
	@CacheEvict(value="taxmanInfo",key="#accId")
	public LogTaxmanInfo createLogTaxmanInfo(String accId) {
		LogTaxmanInfo logTaxmanInfo = new LogTaxmanInfo(accId);
		return logTaxmanInfoDao.saveLogTaxmanInfo(logTaxmanInfo);
	}

	@Override
	@CacheEvict(value="taxmanInfo",key="#logTaxmanInfo.accId")
	public boolean deleteLogTaxmanInfo(LogTaxmanInfo logTaxmanInfo) {
		return logTaxmanInfoDao.deleteLogTaxmanInfo(logTaxmanInfo);
	}

	@Override
	@Cacheable(value = "taxmanInfo",key = "#accId",unless = "#result == null")
	public LogTaxmanInfo findUserLogTaxmanInfoByAccId(String accId) {
		return logTaxmanInfoDao.findLogTaxmanInfoByAccId(accId);
	}

	@Override
	public List<NsrAndTaskInfo> getNsrAndTaskInfoListByCstIds(List<String> cstIds) {
		return logTaxmanInfoDao.getNsrAndTaskInfoListByCstIds(cstIds);
	}
}
