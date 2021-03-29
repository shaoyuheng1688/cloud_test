package com.raymon.taxguide.action.imp;


import com.raymon.taxguide.action.ActionEvent;
import com.raymon.taxguide.model.LogTaxmanInfo;

public class ManualCallActionEvent extends ActionEvent {

	private String cstId;

	public ManualCallActionEvent(LogTaxmanInfo source,String cstId) {
		super(source,"MANUAL_CALL_ACTION");
		this.cstId = cstId;
	}

	public String getCstId(){
		return cstId;
	}
}
