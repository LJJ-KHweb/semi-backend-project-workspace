package com.tri.evre.shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
// 관리자 메인 페이지에서 요일별 상품 구매 차트 응답 DTO
public class WeeklyProductPurchaseDto {
	private String day;			//요일 정보를 담을 변수
	private int purchaseCount;	//요일별 상품 구매수(SUM)을 담을 변수

}
