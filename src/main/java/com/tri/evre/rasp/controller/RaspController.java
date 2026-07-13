package com.tri.evre.rasp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.rasp.model.dto.RaspDto;
import com.tri.evre.rasp.model.dto.RaspResponseDto;
import com.tri.evre.rasp.model.service.RaspService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/rasp")
@RequiredArgsConstructor
public class RaspController {
	
	private final RaspService raspService;
	
	
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> save(@RequestBody RaspDto rasp){
		raspService.save(rasp);
		return ResponseEntity.status(CustomHttpStatus.CREATE_SUCCESS.getCode()).body(ApiResponse.created("주행 데이터 등록에 성공했습니다.", null));
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<RaspResponseDto>>> findAll(){
		List<RaspResponseDto> results = raspService.findAll(); 
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("라즈베리 데이터 조회에 성공했습니다.", results));
	}
	
	@GetMapping("/mypage") // 사용자의 마이페이지 라즈베리파이 
	public ResponseEntity<ApiResponse<List<RaspResponseDto>>> findMyRaspAll(@AuthenticationPrincipal CustomUserDetails user){
		List<RaspResponseDto> results = raspService.findMyRaspAll(user);
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("마이페이지 라즈베리파이 조회 성공했습니다.", results));
	}
	

}
