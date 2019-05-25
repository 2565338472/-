package com.sansan.toothbrush.machine.base;

import org.springframework.validation.BindingResult;

import com.sansan.toothbrush.machine.enums.StatusCode;

/**
 * api格式封装
 * TangJM
 *
 */
public class ApiResponse {

	private int code;
	private String message;
	private Object data;
	private boolean more;

	public ApiResponse() {
		super();
	}
	
	public ApiResponse(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public boolean isMore() {
		return more;
	}
	public void setMore(boolean more) {
		this.more = more;
	}
	
	public static ApiResponse ofMessage(int code, String message) {
		return new ApiResponse(code, message, null);
	}
	
	public static ApiResponse ofMessage(int code, String message, Object data) {
		return new ApiResponse(code, message, data);
	}
	
	public static ApiResponse ofMessage(StatusCode statusCode, String message) {
		return new ApiResponse(statusCode.getCode(), message, null);
	}
	
	/**
	 * 参数校验错误时使用，返回错误信息
	 * @param bindingResult
	 * @return
	 */
	public static ApiResponse ofParamError(BindingResult bindingResult) {
		return new ApiResponse(StatusCode.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage(), null);
	}
	
	public static ApiResponse ofParamError(String message){
		return new ApiResponse(StatusCode.PARAM_ERROR.getCode(), message ,null);
	}
	
	public static ApiResponse ofError(String message){
		return new ApiResponse(StatusCode.ERROR.getCode(), message ,null);
	}
	
	public static ApiResponse ofSuccess(Object data) {
		return new ApiResponse(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getStandardMessage(), data);
	}
	
	public static ApiResponse ofSuccess(String message) {
		return new ApiResponse(StatusCode.SUCCESS.getCode(), message, null);
	}
	
	public static ApiResponse ofStatus(StatusCode statusCode) {
		return new ApiResponse(statusCode.getCode(), statusCode.getStandardMessage(), null);
	}
	
	
}
