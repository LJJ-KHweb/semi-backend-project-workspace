package com.tri.evre.rank.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.shop.model.dto.PurchaseProductDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ranks")
public class RankController {

	@GetMapping("/ranking")
	public ResponseEntity<ApiResponse<List<PurchaseProductDto>>> findAllPurchaseProduct(){
		
		List<PurchaseProductDto> response = adminService.findAllPurchaseProduct();
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("상품 랭킹 조회에 성공했습니다.", response));
	}
}
