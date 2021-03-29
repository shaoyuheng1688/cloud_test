package com.raymon.taxguide.action;

public interface AbstractAction<T extends ActionEvent> {
	/**
	 * 票池标记
	 */
	public static final int TAXGUIDE_CHANNEL = 5;
	public String getName();
	public void act(ActionEvent event);
}
