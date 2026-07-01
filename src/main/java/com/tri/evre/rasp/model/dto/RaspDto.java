package com.tri.evre.rasp.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RaspDto {

	@NotBlank(message="거리값이 안들어옴")
	private double distance;
	@NotBlank(message="전력량 안들어옴")
	private double kilowatt;
	@NotBlank(message="차량번호 안들어옴")
	private String carNum;
	@NotBlank(message="시간 안들어옴")
	private LocalDateTime currentTime;
}
