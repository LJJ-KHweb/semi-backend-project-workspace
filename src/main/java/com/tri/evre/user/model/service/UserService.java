package com.tri.evre.user.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.user.ConcurrentUpdateException;
import com.tri.evre.global.exception.user.DuplicateResourceException;
import com.tri.evre.global.exception.user.PasswordMismatchException;
import com.tri.evre.global.exception.user.UserNotFoundException;
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
		//아이디가 중복인지 확인
		validateDuplicateUserId(user.getUserId());

		User userEntity = User.builder().userId(user.getUserId()).userPwd(passwordEncoder.encode(user.getUserPwd()))
				.userName(user.getUserName()).email(user.getEmail()).build();
		
		userMapper.signup(userEntity);

	}

	public void update(@Valid UserUpdateRequestDto updateUser, CustomUserDetails user) {

		// 아이디 중복체크(회원인지 확인)
		ensureUserIdNotExists(user.getUsername());
		
		// 회원까지는 맞음 비밀번호가 일치하는지 
		checkPwd(updateUser.getUserPwd(), user.getPassword());
		
		//비밀번호까지 일치함 그럼 db에서 회원 정보 수정하기 (이메일과 비번)
		User userEntity = User.builder().userId(user.getUsername())
									.userPwd(passwordEncoder.encode(updateUser.getRawPwd()))
									.email(updateUser.getEmail())
									.build();
		
		
		// 처리를 해줘야 하는 이유는 만약 동시 요청이 들어올 경우가있음
		// 회원 탈퇴를 하는 동시에 변경요청이 들어온다면 UpdateRow가 0이 찍힘
		int result = userMapper.update(userEntity);
		if(result < 1) {
			throw new ConcurrentUpdateException("회원 정보 수정에 실패했습니다");
		}
	}

	// 회원인지 확인하는 방법
	private void ensureUserIdNotExists(String userId) {
		if(userMapper.validateDuplicateUserId(userId) == 0) {
			throw new UserNotFoundException("일치하는 회원이 없습니다.");
		}
	}
	
	// 아이디 중복은 여러군대에서 쓸거 같아서 책임 분리 해놈
	private void validateDuplicateUserId(String userId) {
		if (userMapper.validateDuplicateUserId(userId) > 0) {
			// 예외 처리 아이디가 중복됨
			throw new DuplicateResourceException("이미 사용중인 아이디입니다");
		}
	}
	// 비밀번호 검증 확인 나중에 삭제 같은거 할때 또 필요할거 같아서 분리 해놈
	private void checkPwd(String rawPwd, String encodePassword) {
		if(!passwordEncoder.matches(rawPwd, encodePassword)) {
			throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
		}
	}
	
	private void checkEmail(String email) {
		
	}

}
