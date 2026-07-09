package com.tri.evre.shop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.shop.model.dto.HistoryPurchaseListDto;
import com.tri.evre.shop.model.dto.ProductListResponse;
import com.tri.evre.shop.model.service.ShopService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop")
public class ShopController {
	
	private final ShopService shopService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<ProductListResponse>> findAll(@RequestParam("page") int page,
																	@RequestParam("size") int size){
		
		ProductListResponse productResponse = shopService.findAll(page, size);
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("조회성공", productResponse));
	}
		
	@PatchMapping("/{productNo}")
	public ResponseEntity<ApiResponse<Void>> purchase(@PathVariable(value = "productNo") Long productNo,
													  @AuthenticationPrincipal CustomUserDetails user){
		
		log.info("{}", productNo);
		shopService.purchase(productNo, user);
		
		return ResponseEntity.status(CustomHttpStatus.UPDATE_SUCCESS.getCode()).body(ApiResponse.success("구매 성공", null));
	}
	
	// ------------------------07/01--------------------------
	
	@GetMapping("/his-products")
	public ResponseEntity<ApiResponse<HistoryPurchaseListDto>> findByHistoryPurchase(@RequestParam("page") int page
																					  ,@RequestParam("size") int size
																					  ,@AuthenticationPrincipal CustomUserDetails user){
		
		HistoryPurchaseListDto response = shopService.findByHistoryPurchase(page, size, user);
		
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("조회 성공", response));
	}
	
	

	
	
	

	
}


