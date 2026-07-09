package com.tri.evre.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.user.model.dto.DrivingHistory;
import com.tri.evre.user.model.dto.UserDto;
import com.tri.evre.user.model.dto.UserMileageRequestDto;
import com.tri.evre.user.model.dto.UserUpdateRequestDto;
import com.tri.evre.user.model.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Valid UserDto user){
	
		userService.signup(user);
		return ResponseEntity.status(200).body(ApiResponse.created("회원가입에 성공했습니다.",null));
	}
	
	// 회원 정부수정
	@PatchMapping("/mypage")  
	public ResponseEntity<ApiResponse<Void>> update(@RequestBody @Valid UserUpdateRequestDto updateUser, @AuthenticationPrincipal CustomUserDetails user){
		log.info("@@@@@@@@@@@@@@@@@@@@하이{}", updateUser);
		userService.update(updateUser, user);
		return ResponseEntity.status(CustomHttpStatus.UPDATE_SUCCESS.getCode()).body(ApiResponse.created("회원 정보 수정에 성공하셨습니다.", null));
	}
	
	// 마이페이지 마일리지 적립현황 명세템플릿
	@GetMapping("/mypage")
	public ResponseEntity<ApiResponse<UserMileageRequestDto>> findAllMileageHistory(@RequestParam(name="page", defaultValue ="1")int page,
																					@RequestParam(name="size") int size,
																					@AuthenticationPrincipal CustomUserDetails user){
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("마일리지 조회에 성공했습니다.", userService.findAllMileageHistory(new PageInfo(page, size), user)));
	}
	
	// 차량 이용정보 등록
	@PostMapping("/drivingHistory")
	public ResponseEntity<ApiResponse<Void>> saveDrivingHistory(@AuthenticationPrincipal CustomUserDetails user, 
																@RequestBody @Valid DrivingHistory drivingHistory){
		userService.saveDrivingHistory(user, drivingHistory);
		return ResponseEntity.status(CustomHttpStatus.CREATE_SUCCESS.getCode()).body(ApiResponse.created("마일리지 적립에 성공했습니다.", null));
	}
	
	
}
