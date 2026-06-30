package com.tri.evre.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tri.evre.admin.model.service.AdminService;
import com.tri.evre.board.model.dto.BoardDto;
import com.tri.evre.board.model.dto.BoardListResponse;
import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping("/boards")
	public ResponseEntity<ApiResponse<BoardListResponse>> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
												@RequestParam(name = "size", defaultValue = "3") int size) {
		BoardListResponse boardListResponse = adminService.findAll(new PageInfo(page, size));
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("(관리자)게시글 조회 성공", boardListResponse));
	}
	
	@GetMapping("/boards/{boardNo}")
	public ResponseEntity<ApiResponse<BoardDto>> findByBoard(@PathVariable(name="boardNo") Long boardNo){
		
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("게시판 상세조회 성공", adminService.findByBoard(boardNo)));
	}
}
