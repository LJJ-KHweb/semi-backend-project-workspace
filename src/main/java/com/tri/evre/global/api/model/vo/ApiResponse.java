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
	public static ApiResponse<Void> created(String msg) {
		return new ApiResponse<>(, msg, null);
	}
	
	public static<T> ApiResponse<T> success(String msg, T data) {
		return new ApiResponse<T>(200,msg,data);	
	}
	
}
