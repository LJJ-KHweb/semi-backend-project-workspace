package com.tri.evre.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductListDto {
	private Long productNo;
	private String productName;
	private int amount;
	private int price;
	private String image;
}
