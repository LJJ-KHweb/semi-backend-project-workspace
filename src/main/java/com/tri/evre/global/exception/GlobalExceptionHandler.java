package com.tri.evre.global.exception;


import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/*@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handlerArgumentNotValid(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap();
		e.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
		
		return ResponseEntity.badRequest().body(new (400,"유효하지 않은 값입니다", errors));
	}*/

	/*@ExceptionHandler(DuplicateMemberIdException.class)
	public ResponseEntity<ErrorResponse> handlerDuplicateId(DuplicateMemberIdException e) {
		ErrorResponse er = new ErrorResponse(400, e.getMessage(), null);
		return ResponseEntity.badRequest().body(er);
	}*/

	

}

//실습 겸 숙제
// 오늘 했던 작업을 그대로 반복
// 새 프로젝트 만들기 -> 새 회원용 테이블 만들기
// 새 회원 가입 기능 만들기
