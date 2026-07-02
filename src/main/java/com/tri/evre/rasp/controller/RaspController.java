package com.tri.evre.rasp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.rasp.model.dto.RaspDayOfWeek;
import com.tri.evre.rasp.model.dto.RaspDto;
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
	public ResponseEntity<ApiResponse> save(@RequestBody RaspDto rasp){
		raspService.save(rasp);
		return ResponseEntity.status(CustomHttpStatus.CREATE_SUCCESS.getCode()).body(ApiResponse.created("라즈베리 저장 성공", null));
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<RaspDayOfWeek>>> findAll(){
		List<RaspDayOfWeek> results = raspService.findAll(); 
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("라즈베리 조회에 성공했습니다.", results));
	}
	
	@GetMapping("/mypage") // 사용자의 마이페이지 라즈베리파이 
	public ResponseEntity<ApiResponse<List<RaspDayOfWeek>>> findMyRaspAll(@AuthenticationPrincipal CustomUserDetails user, @RequestParam(name="page", defaultValue="1") int page, @RequestParam(name="size") int size){
		List<RaspDayOfWeek> results = raspService.findMyRaspAll(user, new PageInfo(page, size));
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("마이페이지 라즈베리파이 조회 성공", results));
	}
	

}
