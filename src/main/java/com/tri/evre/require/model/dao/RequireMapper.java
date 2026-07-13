package com.tri.evre.require.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.require.model.dto.RequireDto;
import com.tri.evre.require.model.vo.Require;
import com.tri.evre.require.model.vo.RequireDetailResponse;
import com.tri.evre.require.model.vo.RequireResponse;

@Mapper
public interface RequireMapper {

	int wirteRequire(Require require);

	List<RequireResponse> findAll(@Param("pageInfo")PageInfo pageInfo,@Param("userId") String userId);

	int findRequiresCount(String userId);
	
	int findAllRequireCounts();

	List<Require> adminFindAllRequires(PageInfo pageInfo);

	RequireDto findByBoardNo(@Param("requireNo")Long requireNo,
							 @Param("userId") String userId);

	RequireDto findByRequireNoAdmin(Long requireNo);

	int sumRequires();

	int finishRequires();
	
}
