package com.tri.evre.product.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {
	private String userId;
	@Size(max=20, message="상품 명은 20글자를 넘길 수 없습니다.")
	private String productName;
	private Integer price;
	private Integer amount;
}
