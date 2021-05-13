package com.raymon.taxguide.vo;

import com.raymon.taxguide.web.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 结果集 （接口返回的data为对象，用于GSON）
 * 
 * @param <T>
 *
 */
@Slf4j
public class GsonObjResult<T> {

	public static final int error = 1;

	private String Message;
	private int Code;
	private String ServerTime;
	private T Data;

	public GsonObjResult() {

	}

	public GsonObjResult(int code, String message) {
		this.Code = code;
		this.Message = message;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}

	public String getServerTime() {
		return ServerTime;
	}

	public void setServerTime(String serverTime) {
		ServerTime = serverTime;
	}

	public T getData() {
		return Data;
	}

	public void setData(T data) {
		Data = data;
	}

	public static <T> T getFeignData(GsonObjResult gsonObjResult) {
		if (gsonObjResult != null && gsonObjResult.getCode() == 0) {
			return (T) gsonObjResult.getData();
		} else {
			String message = "远程调用异常，请稍后再试！";
			if (gsonObjResult != null && gsonObjResult.getMessage() != null) {
				message = gsonObjResult.getMessage();
			}
			log.error(message);
			throw new ApplicationException(message);
		}
	}
}
