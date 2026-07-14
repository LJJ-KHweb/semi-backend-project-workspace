package com.tri.evre.global.api.model.vo;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

	private int code;
	private String msg;
	private T data;
	//insert
	public static<T> ApiResponse<T> created(String msg, T data) {
		return new ApiResponse<T>(201, msg, data);
	}
	
	
	public static<T> ApiResponse<T> success(String msg, T data) {
		return new ApiResponse<T>(200,msg,data);	
	}
	public static<T> ApiResponse<T> update(String msg, T data) {
		return new ApiResponse<T>(201,msg,data);	
	}
	
    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
	
	
}
