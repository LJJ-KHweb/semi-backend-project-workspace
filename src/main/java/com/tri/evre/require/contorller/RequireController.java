package com.tri.evre.require.contorller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.file.model.dto.RequireListResponse;
import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.require.model.dto.RequireDto;
import com.tri.evre.require.model.service.RequireService;
import com.tri.evre.require.model.vo.RequireDetailResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/requires")
public class RequireController {

	private final RequireService requireService;

	@PostMapping
	public ResponseEntity<ApiResponse<?>> writeRequire(@ModelAttribute @Valid RequireDto require,
			@RequestParam(name = "file", required = false) List<MultipartFile> files,
			@AuthenticationPrincipal CustomUserDetails user) {
		requireService.writeRequire(require, files, user);

		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode())
				.body(ApiResponse.success("문의사항 작성 성공", null));
	}
	@GetMapping
	public ResponseEntity<ApiResponse<?>> findAll(@RequestParam("page") int page, @RequestParam("size") int size,
			@AuthenticationPrincipal CustomUserDetails user) {

		RequireListResponse requireResponse = requireService.findAll(new PageInfo(page, size), user.getUsername());

		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode())
				.body(ApiResponse.success("문의사항 조회 성공", requireResponse));
	}
	// 문의사항 상세보기
	@GetMapping("{requireNo}")
	public ResponseEntity<ApiResponse<?>> findByRequireNo(@PathVariable("requireNo") Long requireNo,
														  @AuthenticationPrincipal CustomUserDetails user) {
		
		RequireDetailResponse response = requireService.findByRequireNo(requireNo, user.getUsername());
		
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("문의사항 개별조회 성공", response));
	}

}
