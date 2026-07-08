package com.tri.evre.mileage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MileageHistoryResponseDto {
	private String productName;
	private String createDate;
	private int change;
}
