package com.tri.evre.user.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tri.evre.global.exception.user.DuplicateResourceException;
import com.tri.evre.user.model.dao.UserMapper;
import com.tri.evre.user.model.dto.UserDto;
import com.tri.evre.user.model.vo.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
	public void signup(UserDto user) {
		log.info("----------------------------------------------------------------------------");
		int result = userMapper.checkId(user.getUserId());
		
		if(result > 0 ) {
			//예외 처리 아이디가 중복됨
			throw new DuplicateResourceException("이미 사용중인 아이디입니다");
		}
		
		User userEntity = User.builder().userId(user.getUserId())
									.userPwd(passwordEncoder.encode(user.getUserPwd()))
									.userName(user.getUserName())
									.email(user.getEmail())
									.build();
		
		log.info("{}",userEntity);
		
		result = userMapper.signup(userEntity);
		
		
	}
	
}
