package com.raymon.taxguide.action.imp;

import com.raymon.taxguide.action.AbstractAction;
import com.raymon.taxguide.action.ActionEvent;
import com.raymon.taxguide.manager.TaxmanAgentActionManager;
import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.model.TaxguideRecord;
import com.raymon.taxguide.model.TaxguideRecordInfo;
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
public class GiveupAction implements AbstractAction<CallActionEvent> {

	@Autowired
	private TaxmanAgentActionManager taxmanAgentActionManager;
	@Autowired
	private TaxguideRepository taxguideRepository;
	@Autowired
	private UserRepository userRepository;

	/**
	 * 弃号
	 */
	@Override
	public void act(ActionEvent event) {
		GiveupActionEvent giveupActionEvent = (GiveupActionEvent)event;
		LogTaxmanInfo ti = giveupActionEvent.getSource();
		if("IDLE".equals(ti.getAction())){
			throw new ApplicationException("弃号失败，客户端状态异常！ID：" + ti.getTiId());
		}
		TaxguideRecord taxguideRecord = taxguideRepository.findTaxguideRecordById(ti.getCurrentTgId());
		TaxguideRecordInfo taxguideRecordInfo = taxguideRepository.findTaxguideRecordInfoById(taxguideRecord.getCurrentTgInfoId());
		taxguideRecordInfo.setState(TaxguideRecord.State.GIVEUP);
		taxguideRecord.setState(TaxguideRecord.State.GIVEUP);
		taxguideRecord.setEndTime(Calendar.getInstance().getTime());
		taxguideRepository.updateTaxguideRecord(taxguideRecord);
		taxguideRepository.updateTaxguideRecordInfo(taxguideRecordInfo);
		ti.setAction("IDLE");
		ti.setCurrentTgId(null);
		userRepository.updateLogTaxmanInfo(ti);
	}

	@Override
	public String getName() {
		return "GIVEUP_ACTION";
	}
	
	@PostConstruct
	private void register(){
		taxmanAgentActionManager.registerAction(getName(), this);
	}
}