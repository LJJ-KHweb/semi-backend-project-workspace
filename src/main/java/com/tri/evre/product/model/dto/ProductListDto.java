package com.tri.evre.product.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductListDto {
	private Long productNo;
	private String productName;
	private int amount;
	private int price;
	private String iamge;
}
