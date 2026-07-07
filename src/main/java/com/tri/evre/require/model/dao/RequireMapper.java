package com.tri.evre.require.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.require.model.vo.Require;
import com.tri.evre.require.model.vo.RequireResponse;

@Mapper
public interface RequireMapper {

	int wirteRequire(Require require);

	List<RequireResponse> findAll(@Param("pageInfo")PageInfo pageInfo,@Param("userId") String userId);

	int findRequiresCount();

	List<RequireResponse> adminFindAllRequires(PageInfo pageInfo);
	
	
}
