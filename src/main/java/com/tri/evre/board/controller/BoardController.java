package com.tri.evre.board.controller;

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

import com.tri.evre.board.model.dto.BoardDto;
import com.tri.evre.board.model.dto.BoardListResponse;
import com.tri.evre.board.model.service.BoardService;
import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
	
	private final BoardService boardService;
	
	//게시글 작성
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> save(@ModelAttribute @Valid BoardDto board, 
												@RequestParam(name="file") List<MultipartFile> files, 
												@AuthenticationPrincipal CustomUserDetails user){
		
		boardService.save(board,files,user);
		return ResponseEntity.status(CustomHttpStatus.CREATE_SUCCESS.getCode()).body(ApiResponse.created("게시글 작성 성공", null));
	}
	
	//게시글 전체조회
	@GetMapping
	public ResponseEntity<ApiResponse<BoardListResponse>> findAll(@RequestParam("page") int page,
			 													@RequestParam(name="size") int size){
		BoardListResponse boardsResponse = boardService.findAll(new PageInfo(page,size));
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("조회성공", boardsResponse));
	}
	
	// 게시글 상세조회
	@GetMapping("/{boardNo}")
	public ResponseEntity<ApiResponse<BoardDto>> findByBoardNo(@PathVariable(name="boardNo") Long boardNo){
		BoardDto board = boardService.findByBoardNo(boardNo);
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("개별조회 성공", board));
	}
	
	// 게시글 수정
	@PatchMapping("/{boardNo}")
	public ResponseEntity<ApiResponse<Void>> update(@ModelAttribute @Valid BoardDto board,
													@RequestParam(name="file") List<MultipartFile> files,
													@AuthenticationPrincipal CustomUserDetails user,
													@PathVariable(name="boardNo") Long boardNo){
		board.setBoardNo(boardNo);
		boardService.update(board,files,user);
		return ResponseEntity.status(CustomHttpStatus.UPDATE_SUCCESS.getCode()).body(ApiResponse.success("업데이트 성공", null));
	}

	//게시글 삭제
	@DeleteMapping("/{boardNo}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable(name="boardNo") Long boardNo,
													@AuthenticationPrincipal CustomUserDetails user) {
		boardService.delete(boardNo, user);
		return ResponseEntity.status(CustomHttpStatus.DELETE_SUCCESS.getCode()).body(ApiResponse.success("삭제함ㅋ", null));
	}

	
	//----------------------------------- 06/30 재준추가 --------------------------------------
	
	
	
	
	
	
}
