package com.tri.evre.notice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.tri.evre.notice.model.dto.NoticeListResponse;
import com.tri.evre.notice.model.service.NoticeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;

	// 공지사항 작성
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> save(@ModelAttribute @Valid NoticeDto notice,
													@AuthenticationPrincipal CustomUserDetails user,
													@RequestParam(name = "file", required = false) List<MultipartFile> file) {
		noticeService.save(notice, user, file);
		return ResponseEntity.status(CustomHttpStatus.CREATE_SUCCESS.getCode())
				.body(ApiResponse.created("공지사항 작성에 성공하셨습니다.", null));
	}

	// 공지사항 전체조회
	@GetMapping
	public ResponseEntity<ApiResponse<NoticeListResponse>> findAll(@RequestParam(name = "page") int page,
																@RequestParam(name = "size") int size) {

		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode())
				.body(ApiResponse.success("공지사항 전체조회에 성공했습니다.", noticeService.findAll(new PageInfo(page, size))));
	}

	// 게시글 상세조회
	@GetMapping("/{noticeNo}")
	public ResponseEntity<ApiResponse<NoticeDto>> findByNoticeNo(@PathVariable(name = "noticeNo") Long noticeNo) {
		NoticeDto notice = noticeService.findByNoticeNo(noticeNo);
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode())
				.body(ApiResponse.success("개별조회 성공", notice));
	}

	// 게시글 수정
	@PatchMapping("/{noticeNo}")
	public ResponseEntity<ApiResponse<Void>> update(@ModelAttribute @Valid NoticeDto notice,
													@RequestParam(name = "file", required = false) List<MultipartFile> files, 
													@AuthenticationPrincipal CustomUserDetails user,
													@PathVariable(name = "noticeNo") Long noticeNo) {
		notice.setNoticeNo(noticeNo);
		noticeService.update(notice, files, user);
		return ResponseEntity.status(CustomHttpStatus.UPDATE_SUCCESS.getCode())
				.body(ApiResponse.success("업데이트 성공", null));
	}

	// 게시글 삭제
	@DeleteMapping("/{noticeNo}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable(name = "noticeNo") Long noticeNo,
													@AuthenticationPrincipal CustomUserDetails user) {
		noticeService.delete(noticeNo, user);
		return ResponseEntity.status(CustomHttpStatus.DELETE_SUCCESS.getCode()).body(ApiResponse.success("삭제함ㅋ", null));
	}
}
