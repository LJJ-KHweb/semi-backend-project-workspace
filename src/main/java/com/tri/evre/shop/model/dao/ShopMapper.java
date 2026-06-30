package com.tri.evre.shop.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.shop.model.dto.InventoryDto;
import com.tri.evre.shop.model.dto.ProductDto;
import com.tri.evre.shop.model.dto.ProductListDto;

@Mapper
public interface ShopMapper {

	List<ProductListDto> findAll(PageInfo pageInfo);

	int decrease(Long productNo);

	ProductDto findByProductNo(Long productNo);

	InventoryDto findByInventory(Long productNo);

	int insertHistoryMileage(@Param("user") CustomUserDetails user, @Param("product") ProductDto product);

	int useMileage(CustomUserDetails user, ProductDto product);
	
}
