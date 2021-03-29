package com.raymon.taxguide.action.imp;


import com.raymon.taxguide.action.ActionEvent;
import com.raymon.taxguide.model.LogTaxmanInfo;

public class GiveupActionEvent extends ActionEvent {

	public GiveupActionEvent(LogTaxmanInfo source) {
		super(source,"GIVEUP_ACTION");
	}
}
