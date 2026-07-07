package com.tri.evre.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.tri.evre.charger.model.dto.ChargerDto;
import com.tri.evre.charger.model.dto.ChargerResponse;
import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.file.model.dto.RequireListResponse;
import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.product.MissingInventoryFieldException;
import com.tri.evre.product.model.dto.ProductDto;
import com.tri.evre.product.model.dto.UpdateProductDto;
import com.tri.evre.shop.model.dto.ProductListResponse;
import com.tri.evre.shop.model.dto.PurchaseProductDto;
import com.tri.evre.shop.model.dto.WeeklyProductPurchaseDto;
import com.tri.evre.station.model.dto.StationDto;
import com.tri.evre.station.model.dto.StationSearchRequest;
import com.tri.evre.user.model.dto.UserDto;
import com.tri.evre.user.model.dto.UserRoleRequestDto;

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
	@GetMapping("/products")
	public ResponseEntity<ApiResponse<ProductListResponse>> findAllProduct(@RequestParam(name="page") int page
																   		  ,@RequestParam(name="size") int size){
		
		ProductListResponse response = adminService.findAllProduct(new PageInfo(page, size));
		
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("사용내역 전체 조회 성공", response));
		
	}
	
	// -------------07-02--김선겸--
	//--------------상품 추가 기능---
	
	@PostMapping("/products")
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
	
	//----------------------07/03 김선겸
	// 상품 삭제
	
	@DeleteMapping("/products/{productNo}")
	public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable("productNo") Long productNo){
		
		adminService.deleteProduct(productNo);
		
		return ResponseEntity.status(CustomHttpStatus.DELETE_SUCCESS.getCode()).body(ApiResponse.success("상품 삭제 성공", null));
	}
	
	// 상품 수정
	@PatchMapping("/products/{productNo}")
	public ResponseEntity<ApiResponse<Void>> updateProduct(
	        @PathVariable("productNo") Long productNo,
	        @ModelAttribute @Valid UpdateProductDto product,
	        @RequestParam(name = "file", required = false) MultipartFile file) {
		
		
	    if ((product.getProductName() == null || product.getProductName().isBlank())
	            && product.getPrice() == null
	            && product.getAmount() == null
	            && (file == null || file.isEmpty())) {

	        throw new IllegalArgumentException("수정할 내용이 없습니다.");
	    }
	    
	    
	    adminService.updateProduct(productNo, product, file);

	    return ResponseEntity.status(CustomHttpStatus.UPDATE_SUCCESS.getCode()).body(ApiResponse.success("상품 수정 성공", null));
	}
	
	// ======================== 07/06 김선겸
	// 문의사항 전체조회
	
	@GetMapping("/requires")
	public ResponseEntity<ApiResponse<RequireListResponse>> findAllRequires(@RequestParam(name = "page", defaultValue = "0") int page,
												@RequestParam(name = "size", defaultValue = "3") int size) {
		RequireListResponse requireListResponse = adminService.findAllRequires(new PageInfo(page, size));
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("(관리자)문의사항 조회 성공", requireListResponse));
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
	
	
	// 회원 관리페이지 회원 정보 전체 조회
	@GetMapping("/users")
	public ResponseEntity<ApiResponse<List<UserDto>>> findAllUser(@RequestParam(name="page")int page, 
																	@RequestParam(name="size")int size){ 
		
		
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("[관리자] 회원 전체 조회 성공했습니다.", adminService.findAllUser(new PageInfo(page,size))));
	}
	
	@PatchMapping("users")
	public ResponseEntity<ApiResponse<Void>> updateUserRole(@RequestBody @Valid UserRoleRequestDto user){ 

		adminService.updateUserRole(user);
		return ResponseEntity.status(CustomHttpStatus.UPDATE_SUCCESS.getCode()).body(ApiResponse.success("[관리자] 회원 권한 수정에 성공했습니다.", null));
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//------07/03---심영도 - 충전소 전체 조회
		@GetMapping("/chargeStations")
		public ResponseEntity<ApiResponse<StationSearchRequest>> findAllStations(@RequestParam(name="page") int page
												  							   , @RequestParam(name="size") int size) {
			StationSearchRequest stationRequest = adminService.findAllStations(new PageInfo(page, size));
			
			return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("충전소 목록 조회 성공", stationRequest));
		}
		
		// 07/03 심영도 충전소 작성
		@PostMapping("/chargeStations")
		public ResponseEntity<ApiResponse<Void>> insertStation(@RequestBody @Valid StationDto station) {
			adminService.insertStation(station);
			return ResponseEntity.status(CustomHttpStatus.CREATE_SUCCESS.getCode()).body(ApiResponse.created("충전소 작성 성공", null));
		}
		
		// 07/04 심영도 충전기 상세보기
		@GetMapping("/chargeStations/{stationNo}")
		public ResponseEntity<ApiResponse<StationDto>> findByStationNo(@PathVariable(name="stationNo") Long stationNo){
			return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("충전소 조회 성공", adminService.findByStationNo(stationNo)));
		}
		
		// 07/04 심영도 충전소 수정
		@PatchMapping("/chargeStations/{stationNo}")
		public ResponseEntity<ApiResponse<Void>> updateStation(@PathVariable(name="stationNo") Long stationNo,
															   @RequestBody @Valid StationDto station) {
			adminService.updateStation(stationNo, station);
			return ResponseEntity.status(CustomHttpStatus.UPDATE_SUCCESS.getCode()).body(ApiResponse.created("충전소 수정 성공", null));
		}
		
		// 07/06 심영도 충전소 삭제
		@DeleteMapping("/chargeStations/{stationNo}")
		public ResponseEntity<ApiResponse<Void>> deleteStation(@PathVariable(name="stationNo") Long stationNo) {
			adminService.deleteStation(stationNo);
			return ResponseEntity.status(CustomHttpStatus.DELETE_SUCCESS.getCode()).body(ApiResponse.success("충전소 삭제 성공", null));
		}
		
		// 07/06 심영도 충전기 전체조회
		@GetMapping("/charger")
		public ResponseEntity<ApiResponse<ChargerResponse>> findAllCharger(@RequestParam(name="page") int page
												  		   				 , @RequestParam(name="size") int size) {
			return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode())
					.body(ApiResponse.success("충전기 조회 성공", adminService.findAllCharger(new PageInfo(page, size))));
		}
		
		// 07/06 심영도 충전기 추가
		@PostMapping("/charger")
		public ResponseEntity<ApiResponse<Void>> insertCharger(@RequestParam(name="stationNo") Long stationNo){
			adminService.insertCharger(stationNo);
			return ResponseEntity.status(CustomHttpStatus.CREATE_SUCCESS.getCode())
					.body(ApiResponse.created("충전기 작성성공", null));
		}
		
		// 07/07 심영도 충전기 수정
		@PatchMapping("/charger/{chargerNo}")
		public ResponseEntity<ApiResponse<Void>> updateCharger(@PathVariable(name="chargerNo") Long chargerNo,
															   @RequestBody @Valid ChargerDto charger) {
			adminService.updateCharger(chargerNo, charger);
			return ResponseEntity.status(CustomHttpStatus.UPDATE_SUCCESS.getCode())
					.body(ApiResponse.success("충전기 수정 성공", null));
		}
		
		// 7.7 심영도 충전기 삭제
		@DeleteMapping("/charger/{chargerNo}")
		public ResponseEntity<ApiResponse<Void>> deleteCharger(@PathVariable(name="chargerNo") Long chargerNo) {
			adminService.deleteCharger(chargerNo);
			return ResponseEntity.status(CustomHttpStatus.DELETE_SUCCESS.getCode())
					.body(ApiResponse.success("충전기 삭제 성공", null));
		}
}
