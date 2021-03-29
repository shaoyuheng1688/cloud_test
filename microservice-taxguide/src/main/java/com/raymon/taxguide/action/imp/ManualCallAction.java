package com.raymon.taxguide.action.imp;

import com.raymon.frame.vo.CSTicketVo;
import com.raymon.frame.vo.GsonObjResult;
import com.raymon.frame.web.exception.ApplicationException;
import com.raymon.taxguide.action.AbstractAction;
import com.raymon.taxguide.action.ActionEvent;
import com.raymon.taxguide.manager.TaxmanAgentActionManager;
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
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;


@Component
@Slf4j
public class ManualCallAction extends CallAction implements AbstractAction<CallActionEvent> {

	@Autowired
	private TaxmanAgentActionManager taxmanAgentActionManager;
	@Autowired
	private CsFeignClient csFeignClient;

	/**
	 * 手工选号呼叫
	 */
	@Override
	public void act(ActionEvent event) {
		ManualCallActionEvent callActionEvent = (ManualCallActionEvent)event;
		LogTaxmanInfo ti = callActionEvent.getSource();
		if(!"IDLE".equals(ti.getAction())){
			return;
		}
		CSTicketVo csTicket = getCSTicketByAccIdAndCstId(ti.getAccId(),callActionEvent.getCstId());
		super.callActionRecord(csTicket,ti);
	}

	private CSTicketVo getCSTicketByAccIdAndCstId(String accId,String cstId){
		//出队参数
		CsServerParams csServerParams =  CsServerParams
				.newBuilder()
				.accId(accId)
				.cstId(cstId)
				.build();
		GsonObjResult<CSTicketVo> csResult = csFeignClient.getCSTicketByAccIdAndCstId(csServerParams);
		return ResultUtil.getResultData(csResult);
	}

	@Override
	public String getName() {
		return "MANUAL_CALL_ACTION";
	}
	
	@PostConstruct
	private void register(){
		taxmanAgentActionManager.registerAction(getName(), this);
	}
}