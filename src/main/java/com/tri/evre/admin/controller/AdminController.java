package com.tri.evre.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.admin.model.service.AdminService;
import com.tri.evre.board.model.dto.BoardDto;
import com.tri.evre.board.model.dto.BoardListResponse;
import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.product.MissingInventoryFieldException;
import com.tri.evre.product.model.dto.ProductDto;
import com.tri.evre.shop.model.dto.ProductListResponse;
import com.tri.evre.shop.model.dto.PurchaseProductDto;
import com.tri.evre.shop.model.dto.WeeklyProductPurchaseDto;
import com.tri.evre.station.model.dto.StationSearchRequest;
import com.tri.evre.station.model.vo.Station;

import jakarta.validation.Valid;
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
	
	@DeleteMapping("/boards/{boardNo}")
	public ResponseEntity<ApiResponse<Void>> deleteBoard(@PathVariable(name="boardNo") Long boardNo, @AuthenticationPrincipal CustomUserDetails user){
		adminService.deleteBoard(boardNo,user);
		return ResponseEntity.status(CustomHttpStatus.DELETE_SUCCESS.getCode()).body(ApiResponse.success("게시글 삭제 성공", null));
	}
	
	
	
	
	// -------------07-01--김선겸-- 상품관리 기능중 전체 조회------------------------------- 
	@GetMapping("/proudcts")
	public ResponseEntity<ApiResponse<ProductListResponse>> findAllProduct(@RequestParam(name="page") int page
																   		  ,@RequestParam(name="size") int size){
		
		ProductListResponse response = adminService.findAllProduct(new PageInfo(page, size));
		
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("사용내역 전체 조회 성공", response));
		
	}
	
	// -------------07-02--김선겸--
	//--------------상품 추가 기능---
	
	@PostMapping("/proudcts")
	public ResponseEntity<ApiResponse<Void>> insertProduct(@ModelAttribute @Valid ProductDto product,
														   @RequestParam(name="file", required = false) MultipartFile file,
														   @AuthenticationPrincipal CustomUserDetails user){

		if (file == null || file.isEmpty()) {
		    throw new MissingInventoryFieldException("파일이 없어요");
		}
		
		
		adminService.insertProduct(user, product, file);
		
		return ResponseEntity.status(CustomHttpStatus.CREATE_SUCCESS.getCode())
							 .body(ApiResponse.created("상품 추가에 성공했습니다.", null));
		
	}
	
	
	
	
	
	
	
	// --------- 07-02--이재준-- 관리자 페이지 요일별 총 구매수 차트--------------------------

	
	@GetMapping("/ranking")
	public ResponseEntity<ApiResponse<List<PurchaseProductDto>>> findAllPurchaseProduct(){
		
		List<PurchaseProductDto> response = adminService.findAllPurchaseProduct();
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("상품 랭킹 조회에 성공했습니다.", response));
	}
	
	// ---------07-03--이재준-- 관리자 메인 페이지 요일별 상품 구매 차트 ----------------------
	@GetMapping("/charts")
	public ResponseEntity<ApiResponse<List<WeeklyProductPurchaseDto>>> findByPurchaseCount(){ 
		
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("요일별 상품 구매량 조회 성공했습니다.", adminService.findByPurchaseCount()));
	}
	
	
	
	
	
	
	
	
	//------07/03---심영도 - 충전소 전체 조회
	@GetMapping("/chargeStations")
	public ResponseEntity<ApiResponse<StationSearchRequest>> findAllStations(@RequestParam(name="page") int page
											  							   , @RequestParam(name="size") int size) {
		StationSearchRequest stationRequest = adminService.findAllStations(new PageInfo(page, size));
		
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("충전소 목록 조회 성공", stationRequest));
	}
	
	// 07/03 심영도 충전소 작성
	@PostMapping("/chargeStations/form")
	public ResponseEntity<ApiResponse<Void>> insertStation(@RequestBody @Valid Station station
														 , @AuthenticationPrincipal CustomUserDetails user) {
		adminService.insertStation(station, user);
		return ResponseEntity.status(CustomHttpStatus.CREATE_SUCCESS.getCode()).body(ApiResponse.created("충전소 작성 성공", null));
	}
	
	
	
}
