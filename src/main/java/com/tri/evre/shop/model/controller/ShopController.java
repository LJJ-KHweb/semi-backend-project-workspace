package com.tri.evre.shop.model.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
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
		log.info("--------------------------------------------{}",productResponse);
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("조회성공", productResponse));
	}
	

	
}
