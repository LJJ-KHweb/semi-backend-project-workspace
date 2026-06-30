package com.tri.evre.shop.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.shop.InsufficientInventoryException;
import com.tri.evre.global.exception.shop.InventoryUpdateException;
import com.tri.evre.global.exception.shop.MileageHistoryCreateException;
import com.tri.evre.global.exception.shop.ProductNotFoundException;
import com.tri.evre.shop.model.dao.ShopMapper;
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
		pageInfo.setOffset(pageInfo.getPage() * pageInfo.getSize());
		
		List<ProductListDto> products = shopMapper.findAll(pageInfo);
		if(products == null) {
			throw new ProductNotFoundException("조회된 상품이 없습니다.");
		}
		
		return new ProductListResponse(pageInfo, products);
		
	}

	//------------------------------구매-------------
	public void purchase(Long productNo, CustomUserDetails user) {
		
		ProductDto product = shopMapper.findByProductNo(productNo);
		
		if(product == null) {
			throw new ProductNotFoundException("없는 상품을 구매하시려고 하네요");
		}
		
		InventoryDto inventory = shopMapper.findByInventory(productNo);
		
		if(inventory.getAmount() <= 0) {
			throw new InsufficientInventoryException("상품 재고가 부족합니다.");
		}
		
		int result = shopMapper.decrease(productNo);
		
		if(result < 1) {
			throw new InventoryUpdateException("상품 수량 -1를 하는것에 실패했습니다.");
		}
		
		result = shopMapper.insertHistoryMileage(user, product);
		
		if(result < 1) {
			throw new MileageHistoryCreateException("마일리지 내역 추가에 실패했습니다.");
		}
		
		result = shopMapper.useMileage(user, product);
		
		
		
		
		
	}

}
