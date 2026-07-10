package com.tri.evre.rasp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarHistoryDto {
	private String userId;
	private Integer distanceSum;
	private Integer kilowattSum;

}
