package com.tri.evre.product.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	private Long productNo;
	private String userId;
	private String productName;
	private int price;
	private int amount;
}
