package com.tri.evre.global.api.model.vo;

import lombok.Getter;

@Getter
public enum CustomHttpStatus {
	
	// 생성 성공
	CREATE_SUCCESS(201),
	// 조회 성공
	SELECT_SUCCESS(200),
	// 업데이트 성공
	UPDATE_SUCCESS(201),
	// 삭제 성공
	DELETE_SUCCESS(200),
	
	
	
	
	//업데이트 요청시 요청가는 데이터가 하나도 없ㅅ음
	Updated_Nothing(404),
	//---------- 서버 오류--------
	SERVER_ERROR(500),
	
    // ------------------------------
    // 로그인 정보가 일치하지 않음
    INVALID_USER_DATA(401),
    // 요청 데이터의 형식이 올바르지 않음
    INVALID_REQUEST(400),
    // 필수 입력값이 누락됨
    MISSING_REQUIRED_FIELD(400),
    // 이미 존재하는 리소스
    DUPLICATE_RESOURCE(409),
    // Access Token 만료
    ACCESS_TOKEN_EXPIRED(401),
    // Refresh Token 만료
    REFRESH_TOKEN_EXPIRED(401),
    // 회원 정보 수정 실패
    USER_UPDATE_FAIL(409),
    
    
	//---------------------------------------
    // 게시글이 존재하지 않음
    BOARD_NOT_FOUND(404),
    // 게시글 입력 형식이 올바르지 않음
    INVALID_POST(400),
    // 게시글 필수 입력값이 누락됨
    MISSING_POST_FIELD(400),
    // 게시글 작성,수정,삭제 권한이 없음
    BOARD_CUD_FORBIDDEN(403),
    // 이미 삭제된 게시글
    ALREADY_DELETED_BOARD(409),
    // 게시글 CRUD 실패
    BOARD_CRUD_FAILED(500),
    
    //----------------------------------------
    // 파일이 존재하지 않음
    BOARD_FILE_NOT_FOUND(404),
    // 파일 입력값 형식 오류
    INVALID_BOARD_FILE(400),
    // 파일 CRUD 처리 실패 (등록/수정/삭제/조회 통합)
    BOARD_FILE_CRUD_FAILED(500),
	
	//-------------------------------------
    // 답변이 존재하지 않음
    ANSWER_NOT_FOUND(404),
    // 답변 입력 형식이 올바르지 않음
    INVALID_ANSWER(400),
    // 답변 필수 입력값이 누락됨
    MISSING_ANSWER_FIELD(400),
    // 답변 CRUD 실패
    ANSWER_CRUD_FAILED(500),
    
	//------------------------------------
    // 충전소가 존재하지 않음
    STATION_NOT_FOUND(404),
    // 충전소 입력값 형식 오류
    INVALID_STATION(400),
    // 충전소 필수 입력값 누락
    MISSING_STATION_FIELD(400),
    // 충전소 생성 실패
    STATION_CRUD_FAILED(500),
    
    //------------------------------------------
    // 충전기가 존재하지 않음
    CHARGER_NOT_FOUND(404),
    // 충전기 입력값 형식 오류
    INVALID_CHARGER(400),
    // 충전기 필수 입력값 누락
    MISSING_CHARGER_FIELD(400),
    // 충전기 CRUD 처리 실패 (등록/수정/삭제/조회 통합)
    CHARGER_CRUD_FAILED(500),
    // 이미 해당 충전소에 속한 충전기
    DUPLICATE_CHARGER(409),
	
	//----------------------------------------------
    // 상품이 존재하지 않음
    PRODUCT_NOT_FOUND(404),
    // 상품 입력값 검증 실패 (형식 오류, 필수값 누락 등)
    INVALID_PRODUCT(400),
    // 재고 입력값 검증 실패 (수량, 가격, 이미지 등)
    INVALID_INVENTORY(400),
    // 상품 관련 권한 없음 (등록/수정/삭제)
    PRODUCT_FORBIDDEN(403),
    // 이미 등록된 상품
    DUPLICATE_PRODUCT(409),
    // 재고 또는 마일리지 부족
    INSUFFICIENT_RESOURCE(409),
    // 마일리지 내역이 존재하지 않음
    MILEAGE_HISTORY_NOT_FOUND(404),
    // 상품 CRUD 처리 실패 (등록/수정/삭제/조회)
    PRODUCT_CRUD_FAILED(500),
    // 재고 처리 실패 (등록/수정)
    INVENTORY_CRUD_FAILED(500),
    // 마일리지 내역 저장 실패
    MILEAGE_HISTORY_CREATE_FAILED(500) ,
    
    // 07/02 재준 추가 
    RASP_CRUD_FAILED(500),
    INVALID_PAGE_REQUEST(400);
	
	
	
    private final int code;

    CustomHttpStatus(int  code) {
        this.code = code;
    }
	
}
