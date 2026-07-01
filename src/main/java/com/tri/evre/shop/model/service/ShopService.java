package com.tri.evre.shop.model.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.shop.InsufficientInventoryException;
import com.tri.evre.global.exception.shop.InsufficientMileageException;
import com.tri.evre.global.exception.shop.InventoryUpdateException;
import com.tri.evre.global.exception.shop.MileageHistoryCreateException;
import com.tri.evre.global.exception.shop.MileageHistoryNotFoundException;
import com.tri.evre.global.exception.shop.ProductNotFoundException;
import com.tri.evre.global.exception.shop.ProductReadException;
import com.tri.evre.shop.model.dao.ShopMapper;
import com.tri.evre.shop.model.dto.HistoryPurchaseDto;
import com.tri.evre.shop.model.dto.HistoryPurchaseListDto;
import com.tri.evre.shop.model.dto.InventoryDto;
import com.tri.evre.shop.model.dto.ProductDto;
import com.tri.evre.shop.model.dto.ProductListDto;
import com.tri.evre.shop.model.dto.ProductListResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopService {
	
	private final ShopMapper shopMapper;
	
	public ProductListResponse findAll(int page, int size) {
		
		PageInfo pageInfo = new PageInfo(page, size);
		
		List<ProductListDto> products = shopMapper.findAll(pageInfo);
		
		if(products == null||products.isEmpty()) {
			throw new ProductNotFoundException("조회된 상품이 없습니다.");
		}
		
		return new ProductListResponse(pageInfo, products);
		
	}

	//------------------------------구매-------------------------------------
	@Transactional
	public void purchase(Long productNo, CustomUserDetails user) {
		
		ProductDto product = shopMapper.findByProductNo(productNo);
			
		if(product == null) {

			throw new ProductNotFoundException("없는 상품을 구매하시려고 하네요");
		}
		
		
		int result = shopMapper.findHistoryMileage(user);
		
		if(result < 1) {
			throw new MileageHistoryNotFoundException("그냥 마일리지 내역이 없음, 아무것도 없음");
		}
		
		
		
		int totalMileage = shopMapper.findTotalMileage(user);
		
		if(totalMileage + product.getPrice() < 0) {
			throw new InsufficientMileageException("마일리지가 부족합니다.");
		}
		
		
		InventoryDto inventory = shopMapper.findByInventory(productNo);
		
		
		if(inventory.getAmount() <= 0) {
			throw new InsufficientInventoryException("상품 재고가 부족합니다.");
		}
		
		// ---------------------- 책임 분리로 상품 수량을 하나 지우는 건 상품 재고를 찾는거와 다름----------------
		
		result = shopMapper.decrease(productNo);
		
		if(result < 1) {
			throw new InventoryUpdateException("상품 수량 -1를 하는것에 실패했습니다.");
		}
		
		// ----------------------마일리지 내역 추가 실패 --------------------------------
		
		result = shopMapper.insertHistoryMileage(user, product);
		
		if(result < 1) {
			throw new MileageHistoryCreateException("마일리지 내역 추가에 실패했습니다.");
		}
		
	
	}
	
	//------------------------------------07/01---------------------------------------------
	
	// 상품 구매 내역 조회하기

	public HistoryPurchaseListDto findByHistoryPurchase(int page, int size, CustomUserDetails user) {
		
		PageInfo pageInfo = new PageInfo(page, size);
		
		
		List<HistoryPurchaseDto> history = shopMapper.findByHistoryPurchase(pageInfo, user);
		
		if(history == null||history.isEmpty()) {
			throw new MileageHistoryNotFoundException("상품 구매 이력이 없습니다.");
		}
		
		return new HistoryPurchaseListDto(pageInfo, history);
	}

}
