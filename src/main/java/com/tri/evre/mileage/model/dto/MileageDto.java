package com.tri.evre.mileage.model.dto;

import com.tri.evre.common.model.dto.PageInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MileageDto {
	private String productName;
	private String createDate;
	private int price;
	private PageInfo pageInfo;
	
	
}
