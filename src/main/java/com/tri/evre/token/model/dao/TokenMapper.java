package com.tri.evre.token.model.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tri.evre.token.model.vo.RefreshToken;

@Mapper
public interface TokenMapper {

	@Insert("INSERT INTO SEMI_TOKEN VALUES (#{token}, #{expireDate},#{userId})")
	void saveToken(RefreshToken token);
	
	@Delete("DELETE FROM SEMI_TOKEN WHERE USER_ID = #{userId} AND TOKEN = #{refreshToken}")
	int deleteToken(@Param(value = "userId")String userId, @Param(value = "refreshToken") String refreshToken);
	
	@Select("SELECT USER_ID, TOKEN, EXPIRE_DATE FROM SEMI_TOKEN WHERE TOKEN = #{token}")
	RefreshToken findByToken(String token);

}
