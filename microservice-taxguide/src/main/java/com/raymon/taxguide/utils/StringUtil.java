package com.raymon.taxguide.utils;

public class StringUtil {

	/**
	 * 校验字符串是否为空 当param为null或空格时返回true否则返回false
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(String param) {
		boolean flag = false;
		if(param==null|| "".equals(param)){
			flag = true;
		}
		return flag;
	}

}
