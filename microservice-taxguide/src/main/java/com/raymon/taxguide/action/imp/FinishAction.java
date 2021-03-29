package com.raymon.taxguide.action.imp;

import com.raymon.frame.web.exception.ApplicationException;
import com.raymon.taxguide.action.AbstractAction;
import com.raymon.taxguide.action.ActionEvent;
import com.raymon.taxguide.manager.TaxmanAgentActionManager;
import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.model.TaxguideRecord;
import com.raymon.taxguide.model.TaxguideRecordInfo;
import com.raymon.taxguide.remote.CsFeignClient;
import com.raymon.taxguide.repository.TaxguideRepository;
import com.raymon.taxguide.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;


@Component
@Slf4j
public class FinishAction implements AbstractAction<CallActionEvent> {

	@Autowired
	private TaxmanAgentActionManager taxmanAgentActionManager;
	@Autowired
	private TaxguideRepository taxguideRepository;
	@Autowired
	private UserRepository userRepository;

	/**
	 * 办结完成
	 */
	@Override
	public void act(ActionEvent event) {
		FinishActionEvent finishActionEvent = (FinishActionEvent)event;
		LogTaxmanInfo ti = finishActionEvent.getSource();
		if(!"DEALING".equals(ti.getAction())){
			throw new ApplicationException("完成失败，客户端状态异常！ID：" + ti.getTiId());
		}
		TaxguideRecord taxguideRecord = taxguideRepository.findTaxguideRecordById(ti.getCurrentTgId());
		TaxguideRecordInfo taxguideRecordInfo = taxguideRepository.findTaxguideRecordInfoById(taxguideRecord.getCurrentTgInfoId());
		taxguideRecordInfo.setState(TaxguideRecord.State.FINISH);
		taxguideRecordInfo.setFinishTime(Calendar.getInstance().getTime());
		taxguideRecord.setState(TaxguideRecord.State.FINISH);
		taxguideRecord.setEndTime(Calendar.getInstance().getTime());
		taxguideRepository.updateTaxguideRecord(taxguideRecord);
		taxguideRepository.updateTaxguideRecordInfo(taxguideRecordInfo);
		ti.setAction("IDLE");
		ti.setCurrentTgId(null);
		userRepository.updateLogTaxmanInfo(ti);
	}

	@Override
	public String getName() {
		return "FINISH_ACTION";
	}
	
	@PostConstruct
	private void register(){
		taxmanAgentActionManager.registerAction(getName(), this);
	}
}