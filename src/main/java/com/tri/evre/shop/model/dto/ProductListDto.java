package com.tri.evre.shop.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductListDto {
	private Long productNo;
	private String productName;
	private int amount;
	private int change;
	private String iamge;
}
