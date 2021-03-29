package com.raymon.taxguide.action.imp;


import com.raymon.taxguide.action.ActionEvent;
import com.raymon.taxguide.model.LogTaxmanInfo;

public class FinishActionEvent extends ActionEvent {

	public FinishActionEvent(LogTaxmanInfo source) {
		super(source,"FINISH_ACTION");
	}
}
