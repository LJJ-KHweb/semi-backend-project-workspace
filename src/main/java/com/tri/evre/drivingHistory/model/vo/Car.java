package com.tri.evre.car.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class Car {
	private Long boardNo;
	private Date startTime;
	private Date finishTime;
	private String carNo;
}
