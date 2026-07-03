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
public class ProductDto {
	private Long productNo;
	private String userId;
	@NotBlank(message="상품 이름은 공백일 수 없습니다.")
	@Size(max=20, message="상품 명은 20글자를 넘길 수 없습니다.")
	private String productName;
	@NotNull(message="가격은 필수로 입력해야합니다.")
	private Integer price;
	@NotNull(message="수량은 필수로 입력해야합니다.")
	private Integer amount;
}
