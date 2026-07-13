package com.tri.evre.global.auth.model.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.tri.evre.global.auth.model.dto.LoginRequestDto;
import com.tri.evre.global.auth.model.dto.LoginResponse;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.CustomAuthenticationException;
import com.tri.evre.token.model.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {	

	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;

	public LoginResponse login(@Valid LoginRequestDto lrd) {
		// 로그인(인증/Authentication) 구현

		// 1. 유효성 검증(아이디/ 비밀번호값이 들어왔는가? 영어 숫자인가? 글자수가 괜찮은가) -> @Vaild로 대체

		// 2. 아이디가 SEMI_MEMBER 테이블에 MEMBER_ID 컬럼에 존재하는 아이디 인가?
		// 3. 조회를 해온 비밀번호 컬럼의 암호문이 사용자가 입력한 평문으로 만들어진 것이 맞는가?

		Authentication auth = null;

		try {
			auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(lrd.getUserId(), lrd.getUserPwd()));
		} catch (AuthenticationException e) {
			throw new CustomAuthenticationException("아이디 또는 비밀번호가 이상합니다");
		}

		//인증에 성공함
		
		CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
		//log.info("로그인한 사용자의 정보 : {}", user);
		// 토큰 만들어서 발급 
		
		Map<String, String> tokens = tokenService.getTokens(user);
		return LoginResponse.builder().userId(user.getUsername())
												.userName(user.getName())
												.role(user.getAuthorities().toString())
												.accessToken(tokens.get("accessToken"))
												.refreshToken(tokens.get("refreshToken"))
												.build();

	}

}

