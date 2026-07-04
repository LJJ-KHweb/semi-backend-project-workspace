package com.tri.evre.user.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.tri.evre.user.model.vo.User;

@Mapper
public interface UserMapper {

	int validateDuplicateUserId(String userId);
	
	void signup(User userEntity);

	String findPwd();

	int update(User userEntity);

}
