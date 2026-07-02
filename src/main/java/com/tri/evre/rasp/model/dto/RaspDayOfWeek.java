package com.tri.evre.rasp.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NotBlank
@Getter
@Setter
@ToString
public class RaspDayOfWeek {
	
	private String day; 		// 요일의 정보를 담을 변수
	private double distanceSum; // 거리 총합
	private double kilowattSum; // 전기 소모량 총
	private double carbonReduction ;
	
	// 매개변수 생성자를 통해 Mybatis로 값을 매핑해줘서 만들어줌
	public RaspDayOfWeek(String day, double distanceSum, double kilowattSum) {
		super();
		this.day = day;
		this.distanceSum = distanceSum;
		this.kilowattSum = kilowattSum;
	}
	
	
	
}
