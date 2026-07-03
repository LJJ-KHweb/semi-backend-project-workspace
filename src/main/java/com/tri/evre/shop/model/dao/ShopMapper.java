package com.tri.evre.shop.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.product.model.dto.InventoryDto;
import com.tri.evre.product.model.dto.ProductDto;
import com.tri.evre.product.model.dto.ProductListDto;
import com.tri.evre.shop.model.dto.HistoryPurchaseDto;
import com.tri.evre.shop.model.dto.PurchaseProductDto;
import com.tri.evre.shop.model.dto.WeeklyProductPurchaseDto;

@Mapper
public interface ShopMapper {

	List<ProductListDto> findAll(PageInfo pageInfo);

	int decrease(Long productNo);

	ProductDto findByProductNo(Long productNo);

	InventoryDto findByInventory(Long productNo);

	int insertHistoryMileage(@Param("user") CustomUserDetails user, @Param("product") ProductDto product);

	int findTotalMileage(CustomUserDetails user);

	int findHistoryMileage(CustomUserDetails user);

	
	
	
	//----07/01 김선겸-----------------------------------------------------
	
	List<HistoryPurchaseDto> findByHistoryPurchase(@Param("pageInfo")PageInfo pageInfo, 
												   @Param("user") CustomUserDetails user);

	// ---07/02 이재준-----------------------------------------------------
	List<PurchaseProductDto> findAllPurchaseProduct();

	List<WeeklyProductPurchaseDto> findByPurchaseCount();

	// admin용 상품 조회 메소드
	List<ProductListDto> findAllProductAdmin(PageInfo pageInfo);


}
