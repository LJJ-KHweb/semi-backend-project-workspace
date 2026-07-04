package com.tri.evre.notice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.notice.model.dto.NoticeDto;
import com.tri.evre.notice.model.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	
	
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> save(@ModelAttribute NoticeDto notice, 
																@AuthenticationPrincipal CustomUserDetails user, 
																@RequestParam(name="file") MultipartFile file){
		noticeService.save(notice, user, file);
		return ResponseEntity.status(CustomHttpStatus.CREATE_SUCCESS.getCode()).body(ApiResponse.created("공지사항 작성에 성공하셨습니다.", null));
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<NoticeDto>>> findAll(@RequestParam(name="page") int page, @RequestParam(name="size") int size){
		
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("공지사항 전체조회에 성공했습니다.", noticeService.findAll(new PageInfo(page,size))));
	}
}
