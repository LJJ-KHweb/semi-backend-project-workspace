package com.tri.evre.require.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.require.model.vo.Require;

@Mapper
public interface RequireMapper {

	int wirteRequire(Require require);
	

}
