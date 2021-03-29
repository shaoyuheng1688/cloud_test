package com.raymon.taxguide.repository;


import com.raymon.taxguide.model.LogTaxguidePollTime;
import com.raymon.taxguide.model.TaxguideRecord;
import com.raymon.taxguide.model.TaxguideRecordInfo;
import com.raymon.taxguide.model.WorkCalendar;

import java.util.Date;
import java.util.List;

public interface TaxguideRepository {

	TaxguideRecord findTaxguideRecordById(String id);

	TaxguideRecord updateTaxguideRecord(TaxguideRecord taxguideRecord);

	TaxguideRecord saveTaxguideRecord(TaxguideRecord taxguideRecord);

	boolean deleteTaxguideRecord(TaxguideRecord taxguideRecord);

	TaxguideRecordInfo findTaxguideRecordInfoById(long id);

	TaxguideRecordInfo updateTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo);

	TaxguideRecordInfo saveTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo);

	boolean deleteTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo);

	TaxguideRecord findNotFinishTaxguideRecordByBussId(String bussId);

	LogTaxguidePollTime findLogTaxguidePollTimeById(Long id);

	LogTaxguidePollTime updateLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime);

	LogTaxguidePollTime saveLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime);

	boolean deleteLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime);

	LogTaxguidePollTime findLogTaxguidePollTimeByTgIdAndSource(String tgId,int source);

	List<TaxguideRecordInfo> getTaxGuideRecordInfosByBussId(String bussId);

}

