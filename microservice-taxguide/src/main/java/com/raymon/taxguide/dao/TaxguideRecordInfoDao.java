package com.raymon.taxguide.dao;


import com.raymon.taxguide.model.TaxguideRecordInfo;

import java.util.List;

public interface TaxguideRecordInfoDao {

    TaxguideRecordInfo findTaxguideRecordInfoById(long id);

    TaxguideRecordInfo updateTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo);

    TaxguideRecordInfo saveTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo);

    boolean deleteTaxguideRecordInfo(TaxguideRecordInfo taxguideRecordInfo);

    List<TaxguideRecordInfo> getTaxGuideRecordInfosByBussId(String bussId);
}