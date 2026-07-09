package com.tri.evre.user.model.dto;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

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
	@NotNull
	private String carNo;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Timestamp startTime;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Timestamp finishTime;
	//@NotBlank는 문자열 검증용임
	//@NotNull Timestamp 타입이기때문에 문자열 검증을 할수없음 따라서 @NotNull로 검증
	//요청 예시  "startTime": "2026-07-08T14:30:25",
	
	
}
