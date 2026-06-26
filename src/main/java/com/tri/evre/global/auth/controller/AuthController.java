package com.tri.evre.global.auth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.auth.model.dto.LoginRequestDto;
import com.tri.evre.global.auth.model.dto.LoginResponse;
import com.tri.evre.global.auth.model.dto.TestDto;
import com.tri.evre.global.auth.model.service.AuthService;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.token.model.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/auth")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	private final TokenService tokenService;
	
	// 로그인 방식
	// JsonWebToken
	// JWT 프로세스
	// 1. 사용자 인증
	// 사용자가 아이디 및 비밀번호를 입력하고 로그인 요청을 보냄
	// 성공시에 서버는 JWT를 만들어서 클라이언트에게 응답
	// 실패시 서버는 예외를 발생시켜서 JSON예외 객체를 응답해준다
	
	
	// 2. 클라이언트 입장(브라우저)
	// 클라이언트는 로그인 성공 시 응답받은 JWT를 저장소에 보관할거임
	
	// 3. 클라이언트 입장( 토큰 발급 이후 요청시)
	// 요청을 할 때마다 JWT를 해더에 포함시켜서 서버에 전송
	
	// 4. 서버 입장( 토큰 검증)
	// 클라이언트가 보내준 JWT서명을 검증하고 토큰의 유효성 검증(유효기간)
	// 유효성 검사를 통과하면 클레임을 까봐서 권한체크를 하고 요청 처리
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto lrd){
		LoginResponse res = authService.login(lrd);	
		return ResponseEntity.status(200).body(ApiResponse.success("로그인 성공", res));
	}
	
	
	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<Map<String, String>>> refresh(@RequestBody Map<String, String> refreshToken){
		return ResponseEntity.status(201).body(ApiResponse.userCreated(tokenService.tokenRotation(refreshToken.get("refreshToken"))));
		
	}
	
	@DeleteMapping("/logout")
	public ResponseEntity<ApiResponse<Map<String, String>>> logout(@RequestBody Map<String,String> refreshToken, @AuthenticationPrincipal CustomUserDetails user){
		log.info("11111111111111111111111111111111111111111111111111111{}", refreshToken.get("refreshToken"));
		tokenService.logout(refreshToken.get("refreshToken"), user.getUsername());
		return ResponseEntity.status(200).body(ApiResponse.success("로그아웃 성공~",null));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
