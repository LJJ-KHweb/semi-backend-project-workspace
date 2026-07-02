package com.tri.evre.rasp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaspResponseDto {
	private String dayDate;
	private double distanceSum;
	private double carbonReduction;
}
