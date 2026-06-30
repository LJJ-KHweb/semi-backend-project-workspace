package com.tri.evre.global.auth.model.dao;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;

import com.tri.evre.user.model.dto.UserDto;

@Mapper
public interface AuthMapper {

	@Select("""
				SELECT
						USER_ID
					 ,	USER_PWD
					 ,	USER_NAME
					 ,	ROLE
					 ,	STATUS
				  FROM
				  		SEMI_USER
				 WHERE
				 		STATUS = 'Y'
				   AND
				   		USER_ID = #{username}
			
			""")
	UserDto loadUser(String username);
	
}
