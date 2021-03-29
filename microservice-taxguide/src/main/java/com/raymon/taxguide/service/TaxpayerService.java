package com.raymon.taxguide.service;

import com.raymon.taxguide.model.TaxguideRecord;

public interface TaxpayerService {

	TaxguideRecord getTaxguideTask(String bussId);

	TaxguideRecord ready(String bussId);

	TaxguideRecord getState(String tgId);

	void feedback(long tgInfoId,String remark);

}
