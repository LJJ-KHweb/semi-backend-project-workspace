package com.tri.evre.shop.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.exception.shop.ProductNotFoundException;
import com.tri.evre.shop.model.dao.ShopMapper;
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

}
