package com.raymon.taxguide.action.imp;

import javax.annotation.PostConstruct;

import com.raymon.frame.vo.CSTicketVo;
import com.raymon.frame.vo.GsonObjResult;
import com.raymon.frame.web.exception.ApplicationException;
import com.raymon.taxguide.event.CallMessageEvent;
import com.raymon.taxguide.manager.TaxmanAgentActionManager;
import com.raymon.taxguide.action.ActionEvent;
import com.raymon.taxguide.action.AbstractAction;
import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.model.TaxguideRecord;
import com.raymon.taxguide.model.TaxguideRecordInfo;
import com.raymon.taxguide.pojo.cs.CsServerParams;
import com.raymon.taxguide.remote.CsFeignClient;
import com.raymon.taxguide.repository.TaxguideRepository;
import com.raymon.taxguide.repository.UserRepository;
import com.raymon.taxguide.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Calendar;


@Component
@Slf4j
public class CallAction implements AbstractAction<CallActionEvent> {

	@Autowired
	private TaxmanAgentActionManager taxmanAgentActionManager;
	@Autowired
	private CsFeignClient csFeignClient;
	@Autowired
	private TaxguideRepository taxguideRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	/**
	 * 叫号
	 */
	@Override
	public void act(ActionEvent event) {
		CallActionEvent callActionEvent = (CallActionEvent)event;
		LogTaxmanInfo ti = callActionEvent.getSource();
		if(!"IDLE".equals(ti.getAction())){
			return;
		}
		CSTicketVo csTicket = getTicketByAccId(ti.getAccId());
		callActionRecord(csTicket,ti);
	}

	protected void callActionRecord(CSTicketVo csTicket,LogTaxmanInfo ti){
		TaxguideRecord taxguideRecord = taxguideRepository.findTaxguideRecordById(csTicket.getBussId());
		if(null == taxguideRecord || TaxguideRecord.State.WAIT != taxguideRecord.getState()){
			//异常数据处理
			taxguideRecord.setState(TaxguideRecord.State.ERROR);
			taxguideRepository.updateTaxguideRecord(taxguideRecord);
			comfirmDequeueTicket(csTicket.getCsTicketId());
			throw new ApplicationException("call action 异常数据cstId:" + csTicket.getCsTicketId());
		}
		TaxguideRecordInfo taxguideRecordInfo = taxguideRepository.findTaxguideRecordInfoById(taxguideRecord.getCurrentTgInfoId());
		taxguideRecordInfo.setState(TaxguideRecord.State.CALL);
		taxguideRecordInfo.setAccId(ti.getAccId());
		taxguideRecordInfo.setCsTicketId(csTicket.getCsTicketId());
		taxguideRecordInfo.setCallTime(Calendar.getInstance().getTime());
		taxguideRepository.updateTaxguideRecordInfo(taxguideRecordInfo);
		taxguideRecord.setState(TaxguideRecord.State.CALL);
		taxguideRepository.updateTaxguideRecord(taxguideRecord);
		ti.setAction("CALLING");
		ti.setCurrentTgId(csTicket.getBussId());
		userRepository.updateLogTaxmanInfo(ti);
		comfirmDequeueTicket(csTicket.getCsTicketId());
		eventPublisher.publishEvent(new CallMessageEvent(taxguideRecord));
	}

	protected CSTicketVo comfirmDequeueTicket(String cstId){
		//确认出队参数
		CsServerParams csServerParams =  CsServerParams
				.newBuilder()
				.cstId(cstId)
				.build();
		GsonObjResult<CSTicketVo> csResult = csFeignClient.comfirmDequeueTicket(csServerParams);
		return ResultUtil.getResultData(csResult);
	}

	private CSTicketVo getTicketByAccId(String accId){
		//出队参数
		CsServerParams csServerParams =  CsServerParams
				.newBuilder()
				.accId(accId)
				.queueChannelId(TAXGUIDE_CHANNEL)
				.build();
		GsonObjResult<CSTicketVo> csResult = csFeignClient.getTicketByAccId(csServerParams);
		return ResultUtil.getResultData(csResult);
	}

	@Override
	public String getName() {
		return "CALL_ACTION";
	}
	
	@PostConstruct
	private void register(){
		taxmanAgentActionManager.registerAction(getName(), this);
	}
}