package com.raymon.taxguide.dao;


import com.raymon.taxguide.model.TaxguideRecord;

public interface TaxguideRecordDao {

    TaxguideRecord findTaxguideRecordById(String id);

    TaxguideRecord updateTaxguideRecord(TaxguideRecord taxguideRecord);

    TaxguideRecord saveTaxguideRecord(TaxguideRecord taxguideRecord);

    boolean deleteTaxguideRecord(TaxguideRecord taxguideRecord);

    TaxguideRecord findNotFinishTaxguideRecordByBussId(String bussId);
}