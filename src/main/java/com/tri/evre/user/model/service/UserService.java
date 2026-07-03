package com.tri.evre.user.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.user.DuplicateResourceException;
import com.tri.evre.user.model.dao.UserMapper;
import com.tri.evre.user.model.dto.UserDto;
import com.tri.evre.user.model.dto.UserUpdateRequestDto;
import com.tri.evre.user.model.vo.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public void signup(UserDto user) {
		checkId(user.getUserId());

		User userEntity = User.builder().userId(user.getUserId()).userPwd(passwordEncoder.encode(user.getUserPwd()))
				.userName(user.getUserName()).email(user.getEmail()).build();
		
		userMapper.signup(userEntity);

	}

	public void update(@Valid UserUpdateRequestDto updateUser, CustomUserDetails user) {

		checkId(user.getUsername());
		
		String userPwd = userMapper.findPwd();
		
	}

	
	
	// 아이디 중복은 여러군대에서 쓸거 같아서 책임 분리 해놈
	private void checkId(String userId) {
		int result = userMapper.checkId(userId);

		if (result > 0) {
			// 예외 처리 아이디가 중복됨
			throw new DuplicateResourceException("이미 사용중인 아이디입니다");
		}

	}

}
