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
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.global.auth.model.dto.LoginRequestDto;
import com.tri.evre.global.auth.model.dto.LoginResponse;
import com.tri.evre.global.auth.model.dto.LogoutRequest;
import com.tri.evre.global.auth.model.dto.RefreshTokenRequestDto;
import com.tri.evre.global.auth.model.service.AuthService;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.token.model.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	private final TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequestDto lrd){
		LoginResponse res = authService.login(lrd);	
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("로그인에 성공했습니다.", res));
	}
	
	
	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<Map<String, String>>> refresh(@RequestBody @Valid RefreshTokenRequestDto refreshToken){
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("액세스 토큰 재발급에 성공했습니다.",tokenService.tokenRotation(refreshToken.getRefreshToken())));
		
	}
	
	@DeleteMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(@RequestBody @Valid LogoutRequest logoutRequest, @AuthenticationPrincipal CustomUserDetails user){
		tokenService.logout(logoutRequest.getRefreshToken(), user.getUsername());
		return ResponseEntity.status(CustomHttpStatus.DELETE_SUCCESS.getCode()).body(ApiResponse.success("로그아웃에 성공했습니다.",null));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
