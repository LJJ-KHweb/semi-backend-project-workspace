package com.tri.evre.rasp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
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
	
	
	//하하호호
	@PostMapping
	public ResponseEntity<ApiResponse> save(@RequestBody RaspDto rasp){
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{}", rasp);
		
		raspService.save(rasp);
		return ResponseEntity.status(CustomHttpStatus.CREATE_SUCCESS.getCode()).body(ApiResponse.created("라즈베리 저장 성공", null));
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<RaspDayOfWeek>>> findAll(){
		List<RaspDayOfWeek> results = raspService.findAll(); 
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{}",results);
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("라즈베리 조회에 성공했습니다.", results));
	}
	

}
