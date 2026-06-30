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
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto lrd){
		LoginResponse res = authService.login(lrd);	
		return ResponseEntity.status(200).body(ApiResponse.success("로그인 성공", res));
	}
	
	
	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<Map<String, String>>> refresh(@RequestBody Map<String, String> refreshToken){
		return ResponseEntity.status(201).body(ApiResponse.created("엑세스 토큰 발급 성공",tokenService.tokenRotation(refreshToken.get("refreshToken"))));
		
	}
	
	@DeleteMapping("/logout")
	public ResponseEntity<ApiResponse<Map<String, String>>> logout(@RequestBody Map<String,String> refreshToken, @AuthenticationPrincipal CustomUserDetails user){
		log.info("11111111111111111111111111111111111111111111111111111{}", refreshToken.get("refreshToken"));
		tokenService.logout(refreshToken.get("refreshToken"), user.getUsername());
		return ResponseEntity.status(200).body(ApiResponse.success("로그아웃 성공~",null));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
