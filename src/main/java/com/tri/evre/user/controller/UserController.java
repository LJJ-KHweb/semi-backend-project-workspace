package com.tri.evre.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.user.model.dto.UserDto;
import com.tri.evre.user.model.dto.UserUpdateRequestDto;
import com.tri.evre.user.model.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<?> signup(@RequestBody @Valid UserDto user){
	
		userService.signup(user);
		return ResponseEntity.status(200).body(ApiResponse.created("회원가입에 성공했습니다.",null));
	}
	
	@PatchMapping
	public ResponseEntity<ApiResponse<Void>> update(@RequestBody @Valid UserUpdateRequestDto updateUser, @AuthenticationPrincipal CustomUserDetails user){
		userService.update(updateUser, user);
		return ResponseEntity.status(CustomHttpStatus.CREATE_SUCCESS.getCode()).body(ApiResponse.created("회원 정보 수정에 성공하셨습니다.", null));
	}
	
	
}
