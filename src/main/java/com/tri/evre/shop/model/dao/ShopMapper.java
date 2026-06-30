package com.tri.evre.shop.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.shop.model.dto.ProductListDto;

@Mapper
public interface ShopMapper {

	List<ProductListDto> findAll(PageInfo pageInfo);

}
