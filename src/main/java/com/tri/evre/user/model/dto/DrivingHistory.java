package com.tri.evre.user.model.dto;

import java.sql.Timestamp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DrivingHistory {
	
	@NotBlank
	private String carNo;
	@NotNull
	private Timestamp startTime;
	@NotNull
	private Timestamp finishTime;
	//@NotBlank는 문자열 검증용임
	//@NotNull Timestamp 타입이기때문에 문자열 검증을 할수없음 따라서 @NotNull로 검증
	//요청 예시  "startTime": "2026-07-08T14:30:25",
	
	
}
