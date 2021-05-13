package com.raymon.taxguide.repository.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.raymon.taxguide.mapper.LogTaxmanInfoMapper;
import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.pojo.NsrAndTaskInfo;
import com.raymon.taxguide.repository.UserRepository;
import com.raymon.taxguide.utils.DateTimeUtils;
import com.raymon.taxguide.web.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component("userRepository")
public class UserRepositoryImp implements UserRepository {

	@Autowired
	private LogTaxmanInfoMapper logTaxmanInfoMapper;

	@Override
	public LogTaxmanInfo findLogTaxmanInfoById(long id) {
		return logTaxmanInfoMapper.selectById(id);
	}

	@Override
	@CacheEvict(value="taxmanInfo",key="#logTaxmanInfo.accId")
	public LogTaxmanInfo updateLogTaxmanInfo(LogTaxmanInfo logTaxmanInfo) {
		int count = logTaxmanInfoMapper.updateById(logTaxmanInfo);
		if(0 < count ){
			return logTaxmanInfo;
		}else{
			throw new ApplicationException("更新失败！");
		}
	}

	@Override
	@CacheEvict(value="taxmanInfo",key="#accId")
	public LogTaxmanInfo createLogTaxmanInfo(String accId) {
		LogTaxmanInfo logTaxmanInfo = new LogTaxmanInfo(accId);
		Date createDate = Calendar.getInstance().getTime();
		logTaxmanInfo.setCreateTime(createDate);
		logTaxmanInfo.setAction("IDLE");
		int count = logTaxmanInfoMapper.insert(logTaxmanInfo);
		if(0 < count ){
			return logTaxmanInfo;
		}else{
			throw new ApplicationException("保存失败！");
		}
	}

	@Override
	@CacheEvict(value="taxmanInfo",key="#logTaxmanInfo.accId")
	public boolean deleteLogTaxmanInfo(LogTaxmanInfo logTaxmanInfo) {
		int count = logTaxmanInfoMapper.deleteById(logTaxmanInfo);
		if(0 < count ){
			return true;
		}else{
			throw new ApplicationException("删除失败！");
		}
	}

	@Override
	@Cacheable(value = "taxmanInfo",key = "#accId",unless = "#result == null")
	public LogTaxmanInfo findUserLogTaxmanInfoByAccId(String accId) {
		QueryWrapper<LogTaxmanInfo> wrapper = new QueryWrapper<>();
		wrapper.eq("ACC_ID",accId)
				.ge("CREATE_TIME", DateTimeUtils.getTodayStartTime())
				.orderByDesc("CREATE_TIME");
		return logTaxmanInfoMapper.selectPage(new Page<>(1,1,false),wrapper).getRecords().get(0);
	}

	@Override
	public List<NsrAndTaskInfo> getNsrAndTaskInfoListByCstIds(List<String> cstIds) {
		return logTaxmanInfoMapper.getNsrAndTaskInfoListByCstIds(cstIds);
	}
}
