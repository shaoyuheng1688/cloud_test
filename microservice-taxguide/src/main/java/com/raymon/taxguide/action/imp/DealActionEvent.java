package com.raymon.taxguide.action.imp;


import com.raymon.taxguide.action.ActionEvent;
import com.raymon.taxguide.model.LogTaxmanInfo;

public class DealActionEvent extends ActionEvent {

	public DealActionEvent(LogTaxmanInfo source) {
		super(source,"DEAL_ACTION");
	}
}
