package com.raymon.taxguide.action.imp;


import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.action.ActionEvent;

public class CallActionEvent extends ActionEvent {

	public CallActionEvent(LogTaxmanInfo source) {
		super(source,"CALL_ACTION");
	}
}
