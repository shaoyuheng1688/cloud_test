package com.raymon.taxguide.repository.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.raymon.taxguide.mapper.LogTaxguidePollTimeMapper;
import com.raymon.taxguide.mapper.TaxguideRecordInfoMapper;
import com.raymon.taxguide.mapper.TaxguideRecordMapper;
import com.raymon.taxguide.model.LogTaxguidePollTime;
import com.raymon.taxguide.model.TaxguideRecord;
import com.raymon.taxguide.model.TaxguideRecordInfo;
import com.raymon.taxguide.repository.TaxguideRepository;
import com.raymon.taxguide.web.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("taxguideRepository")
public class TaxguideRepositoryImp implements TaxguideRepository {

	@Autowired
	private TaxguideRecordMapper taxguideRecordMapper;

	@Autowired
	private TaxguideRecordInfoMapper taxguideRecordInfoMapper;

	@Autowired
	private LogTaxguidePollTimeMapper logTaxguidePollTimeMapper;

	@Override
	@Cacheable(value = "tgRecord",key = "#id",unless = "#result == null")
	public TaxguideRecord findTaxguideRecordById(String id) {
		return taxguideRecordMapper.selectById(id);
	}

	@Override
	@CacheEvict(value="tgRecord",key="#taxguideRecord.tgId")
	public TaxguideRecord updateTaxguideRecord(TaxguideRecord taxguideRecord) {
		int count = taxguideRecordMapper.updateById(taxguideRecord);
		if(0 < count ){
			return taxguideRecord;
		}else{
		throw new ApplicationException("更新失败！");
		}
	}

	@Override
	@CacheEvict(value="tgRecord",key="#taxguideRecord.tgId")
	public TaxguideRecord saveTaxguideRecord(TaxguideRecord taxguideRecord) {
		int count = taxguideRecordMapper.insert(taxguideRecord);
		if(0 < count ){
			return taxguideRecord;
		}else{
			throw new ApplicationException("保存失败！");
		}
	}

	@Override
	@CacheEvict(value="tgRecord",key="#taxguideRecord.tgId")
	public boolean deleteTaxguideRecord(TaxguideRecord taxguideRecord) {
		int count = taxguideRecordMapper.deleteById(taxguideRecord);
		if(0 < count ){
			return true;
		}else{
			throw new ApplicationException("删除失败！");
		}
	}

	@Override
	@Cacheable(value = "tgRecordInfo",key = "#id",unless = "#result == null")
	public TaxguideRecordInfo findTaxguideRecordInfoById(long id) {
		return taxguideRecordInfoMapper.selectById(id);
	}

	@Override
	@CacheEvict(value="tgRecordInfo",key="#taxguideRecordInfo.tgInfoId")
	public TaxguideRecordInfo updateTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo) {
		int count = taxguideRecordInfoMapper.updateById(taxguideRecordInfo);
		if(0 < count ){
			return taxguideRecordInfo;
		}else{
			throw new ApplicationException("更新失败！");
		}
	}

	@Override
	@CacheEvict(value="tgRecordInfo",key="#taxguideRecordInfo.tgInfoId")
	public TaxguideRecordInfo saveTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo) {
		int count = taxguideRecordInfoMapper.insert(taxguideRecordInfo);
		if(0 < count ){
			return taxguideRecordInfo;
		}else{
			throw new ApplicationException("保存失败！");
		}
	}

	@Override
	@CacheEvict(value="tgRecordInfo",key="#taxguideRecordInfo.tgInfoId")
	public boolean deleteTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo) {
		int count = taxguideRecordInfoMapper.deleteById(taxguideRecordInfo);
		if(0 < count ){
			return true;
		}else{
			throw new ApplicationException("删除失败！");
		}
	}

	@Override
	public LogTaxguidePollTime findLogTaxguidePollTimeById(Long id) {
		return logTaxguidePollTimeMapper.selectById(id);
	}

	@Override
	public LogTaxguidePollTime updateLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime) {
		int count = logTaxguidePollTimeMapper.updateById(logTaxguidePollTime);
		if(0 < count ){
			return logTaxguidePollTime;
		}else{
			throw new ApplicationException("更新失败！");
		}
	}

	@Override
	public LogTaxguidePollTime saveLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime) {
		int count = logTaxguidePollTimeMapper.insert(logTaxguidePollTime);
		if(0 < count ){
			return logTaxguidePollTime;
		}else{
			throw new ApplicationException("保存失败！");
		}
	}

	@Override
	public boolean deleteLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime) {
		int count = logTaxguidePollTimeMapper.deleteById(logTaxguidePollTime);
		if(0 < count ){
			return true;
		}else{
			throw new ApplicationException("删除失败！");
		}
	}

	@Override
	public LogTaxguidePollTime findLogTaxguidePollTimeByTgIdAndSource(String tgId, int source) {
		QueryWrapper<LogTaxguidePollTime> wrapper = new QueryWrapper<>();
		wrapper.eq("TG_ID",tgId)
				.eq("SOURCE", source)
				.orderByDesc("CREATE_TIME");
		return logTaxguidePollTimeMapper.selectOne(wrapper);
	}

	@Override
	public List<TaxguideRecordInfo> getTaxGuideRecordInfosByBussId(String bussId) {
		return taxguideRecordInfoMapper.getTaxGuideRecordInfosByBussId(bussId);
	}

	@Override
	public TaxguideRecord findNotFinishTaxguideRecordByBussId(String bussId) {
		QueryWrapper<TaxguideRecord> wrapper = new QueryWrapper<TaxguideRecord>();
		wrapper.eq("BUSSID",bussId)
				.lt("STATE", 4)
				.orderByDesc("CREATE_TIME");
		return taxguideRecordMapper.selectOne(wrapper);
	}
}
