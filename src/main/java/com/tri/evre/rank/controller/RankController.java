package com.tri.evre.rank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.rank.model.dto.RankResponseDto;
import com.tri.evre.rank.model.service.RankService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ranks")
public class RankController {
	private final RankService rankService;

	@GetMapping("/ranking")
	public ResponseEntity<ApiResponse<RankResponseDto>> findByUserRanking(@RequestParam(name="page") int page, 
																			@RequestParam(name="size") int size,
																			@RequestParam(name="userId") String userId){
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("사용자 랭킹 조회에 성공했습니다.", rankService.findByUserRanking(new PageInfo(page,size),userId)));
	}
}
