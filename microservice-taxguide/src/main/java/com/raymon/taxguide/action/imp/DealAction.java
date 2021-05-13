package com.raymon.taxguide.action.imp;

import com.raymon.taxguide.action.AbstractAction;
import com.raymon.taxguide.action.ActionEvent;
import com.raymon.taxguide.manager.TaxmanAgentActionManager;
import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.model.TaxguideRecord;
import com.raymon.taxguide.model.TaxguideRecordInfo;
import com.raymon.taxguide.remote.CsFeignClient;
import com.raymon.taxguide.repository.TaxguideRepository;
import com.raymon.taxguide.repository.UserRepository;
import com.raymon.taxguide.web.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;


@Component
@Slf4j
public class DealAction implements AbstractAction<CallActionEvent> {

	@Autowired
	private TaxmanAgentActionManager taxmanAgentActionManager;
	@Autowired
	private CsFeignClient csFeignClient;
	@Autowired
	private TaxguideRepository taxguideRepository;
	@Autowired
	private UserRepository userRepository;

	/**
	 * 办结完成
	 */
	@Override
	public void act(ActionEvent event) {
		DealActionEvent dealActionEvent = (DealActionEvent)event;
		LogTaxmanInfo ti = dealActionEvent.getSource();
		if(!"CALLING".equals(ti.getAction())){
			throw new ApplicationException("办理失败，客户端状态异常！ID：" + ti.getTiId());
		}
		TaxguideRecord taxguideRecord = taxguideRepository.findTaxguideRecordById(ti.getCurrentTgId());
		if(TaxguideRecord.State.READY != taxguideRecord.getState()){
			throw new ApplicationException("纳税人端还没准备好！ID：" + taxguideRecord.getTgId());
		}
		TaxguideRecordInfo taxguideRecordInfo = taxguideRepository.findTaxguideRecordInfoById(taxguideRecord.getCurrentTgInfoId());
		taxguideRecordInfo.setState(TaxguideRecord.State.DEAL);
		taxguideRecordInfo.setStartTime(Calendar.getInstance().getTime());
		taxguideRecord.setState(TaxguideRecord.State.DEAL);
		taxguideRepository.updateTaxguideRecord(taxguideRecord);
		taxguideRepository.updateTaxguideRecordInfo(taxguideRecordInfo);
		ti.setAction("DEALING");
		userRepository.updateLogTaxmanInfo(ti);
	}

	@Override
	public String getName() {
		return "DEAL_ACTION";
	}
	
	@PostConstruct
	private void register(){
		taxmanAgentActionManager.registerAction(getName(), this);
	}
}