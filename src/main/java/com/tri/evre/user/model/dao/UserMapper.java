package com.tri.evre.user.model.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tri.evre.user.model.vo.User;

@Mapper
public interface UserMapper {

	
	@Select("""
				SELECT
						COUNT(*)
				  FROM
				  		SEMI_USER
				 WHERE
				 		USER_ID = #{userId}
			""")
	int checkId(String userId);
	
	
	
	@Insert("""
				INSERT
				  INTO
				  		SEMI_USER
				  		(
				  		USER_ID
				  	 ,	USER_PWD
				  	 ,	USER_NAME
				  	 ,	EMAIL
				  	 	)
				VALUES
						(
						#{userId}
					 ,	#{userPwd}
					 ,	#{userName}
					 ,	#{email}
					 	)
			""")
	void signup(User userEntity);

}
