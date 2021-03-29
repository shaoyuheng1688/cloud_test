package com.raymon.taxguide.action;


import com.raymon.taxguide.model.LogTaxmanInfo;

public class ActionEvent {
	private LogTaxmanInfo source;
	private String actionName;
	public ActionEvent(LogTaxmanInfo source,String name){
		this.source = source;
		this.actionName = name;
	}

	public LogTaxmanInfo getSource(){
		return this.source;
	}

	public String getActionName(){
		return this.actionName;
	}
}
