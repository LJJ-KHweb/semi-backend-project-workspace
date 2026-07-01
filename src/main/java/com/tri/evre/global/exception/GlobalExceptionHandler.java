package com.tri.evre.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.global.exception.answer.AnswerCreateException;
import com.tri.evre.global.exception.answer.AnswerDeleteException;
import com.tri.evre.global.exception.answer.AnswerNotFoundException;
import com.tri.evre.global.exception.answer.AnswerReadException;
import com.tri.evre.global.exception.answer.AnswerUpdateException;
import com.tri.evre.global.exception.answer.InvalidAnswerFormatException;
import com.tri.evre.global.exception.answer.MissingAnswerFieldException;
import com.tri.evre.global.exception.board.AlreadyDeletedBoardException;
import com.tri.evre.global.exception.board.BoardCreateException;
import com.tri.evre.global.exception.board.BoardDeleteException;
import com.tri.evre.global.exception.board.BoardDeleteForbiddenException;
import com.tri.evre.global.exception.board.BoardNotFoundException;
import com.tri.evre.global.exception.board.BoardReadException;
import com.tri.evre.global.exception.board.BoardUpdateException;
import com.tri.evre.global.exception.board.BoardUpdateForbiddenException;
import com.tri.evre.global.exception.board.BoardWriteForbiddenException;
import com.tri.evre.global.exception.board.InvalidPostFormatException;
import com.tri.evre.global.exception.board.MissingPostFieldException;
import com.tri.evre.global.exception.board.file.BoardFileCreateException;
import com.tri.evre.global.exception.board.file.BoardFileDeleteException;
import com.tri.evre.global.exception.board.file.BoardFileNotFoundException;
import com.tri.evre.global.exception.board.file.BoardFileReadException;
import com.tri.evre.global.exception.board.file.BoardFileUpdateException;
import com.tri.evre.global.exception.board.file.InvalidBoardFileFormatException;
import com.tri.evre.global.exception.charger.ChargerCreateException;
import com.tri.evre.global.exception.charger.ChargerDeleteException;
import com.tri.evre.global.exception.charger.ChargerNotFoundException;
import com.tri.evre.global.exception.charger.ChargerReadException;
import com.tri.evre.global.exception.charger.ChargerUpdateException;
import com.tri.evre.global.exception.charger.DuplicateChargerException;
import com.tri.evre.global.exception.charger.InvalidChargerFormatException;
import com.tri.evre.global.exception.charger.MissingChargerFieldException;
import com.tri.evre.global.exception.shop.InsufficientInventoryException;
import com.tri.evre.global.exception.shop.InsufficientMileageException;
import com.tri.evre.global.exception.shop.InventoryUpdateException;
import com.tri.evre.global.exception.shop.MileageHistoryNotFoundException;
import com.tri.evre.global.exception.shop.ProductNotFoundException;
import com.tri.evre.global.exception.station.InvalidStationFormatException;
import com.tri.evre.global.exception.station.MissingStationFieldException;
import com.tri.evre.global.exception.station.StationCreateException;
import com.tri.evre.global.exception.station.StationDeleteException;
import com.tri.evre.global.exception.station.StationNotFoundException;
import com.tri.evre.global.exception.station.StationReadException;
import com.tri.evre.global.exception.station.StationUpdateException;
import com.tri.evre.global.exception.user.AccessTokenExprireException;
import com.tri.evre.global.exception.user.DuplicateResourceException;
import com.tri.evre.global.exception.user.MissingAuthFieldException;
import com.tri.evre.global.exception.user.PasswordMismatchException;
import com.tri.evre.global.exception.user.RefreshTokenExprireException;
import com.tri.evre.global.exception.user.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/*
	 * @ExceptionHandler(MethodArgumentNotValidException.class) public
	 * ResponseEntity<ErrorResponse>
	 * handlerArgumentNotValid(MethodArgumentNotValidException e) { Map<String,
	 * String> errors = new HashMap();
	 * e.getBindingResult().getFieldErrors().forEach(err ->
	 * errors.put(err.getField(), err.getDefaultMessage()));
	 * 
	 * return ResponseEntity.badRequest().body(new (400,"유효하지 않은 값입니다", errors)); }
	 */

	// 회원 -----------------------------------------------------------------------

	// 회원 유저 정보가 디비에 없을시
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse> UserNotFound(UserNotFoundException e) {
		return ResponseEntity.status(CustomHttpStatus.INVALID_USER_DATA.getCode())
				.body(new ApiResponse(1000, e.getMessage(), null));
	}

	// 비밀번호가 일치하지 않을시
	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<ApiResponse> PasswordMismatch(PasswordMismatchException e) {
		return ResponseEntity.status(CustomHttpStatus.INVALID_USER_DATA.getCode())
				.body(new ApiResponse(1001, e.getMessage(), null));
	}

	// 아이디 OR 비밀번호 OR 이메일 OR 이름 유효성 검사 실패시
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> MethodArgumentNotValid(MethodArgumentNotValidException e) {
		return ResponseEntity.status(CustomHttpStatus.INVALID_REQUEST.getCode())
				.body(new ApiResponse(1002, e.getMessage(), null));
	}

	// 필수입력값이 앞단에서 null값으로 넘어왔을 경우
	@ExceptionHandler(MissingAuthFieldException.class)
	public ResponseEntity<ApiResponse> MissingAuthField(MissingAuthFieldException e) {
		return ResponseEntity.status(CustomHttpStatus.MISSING_REQUIRED_FIELD.getCode())
				.body(new ApiResponse(1003, e.getMessage(), null));
	}

	// 아이디와 이메일(UNIQUE)속성에 값이 중복되었을 경우
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiResponse> DuplicateResource(DuplicateResourceException e) {
		return ResponseEntity.status(CustomHttpStatus.DUPLICATE_RESOURCE.getCode())
				.body(new ApiResponse(1004, e.getMessage(), null));
	}

	// Access Token이 만료됨
	@ExceptionHandler(AccessTokenExprireException.class)
	public ResponseEntity<ApiResponse> AccessTokenExprire(AccessTokenExprireException e) {
		return ResponseEntity.status(CustomHttpStatus.ACCESS_TOKEN_EXPIRED.getCode())
				.body(new ApiResponse(1005, e.getMessage(), null));
	}

	// Refresh Tokne 이 만료됨
	@ExceptionHandler(RefreshTokenExprireException.class)
	public ResponseEntity<ApiResponse> RefreshTokenExprire(RefreshTokenExprireException e) {
		return ResponseEntity.status(CustomHttpStatus.REFRESH_TOKEN_EXPIRED.getCode())
				.body(new ApiResponse(1006, e.getMessage(), null));
	}

	// 일반 게시판
	// -----------------------------------------------------------------------------------

	// 일치하는 게시글이 없습니다.
	@ExceptionHandler(BoardNotFoundException.class)
	public ResponseEntity<ApiResponse> BoardNotFound(BoardNotFoundException e) {
		return ResponseEntity.status(CustomHttpStatus.BOARD_NOT_FOUND.getCode())
				.body(new ApiResponse(2000, e.getMessage(), null));
	}

	// 게시글 제목 형식이 올바르지 않습니다.
	@ExceptionHandler(InvalidPostFormatException.class)
	public ResponseEntity<ApiResponse> invalidPostFormat(InvalidPostFormatException e) {
		return ResponseEntity.status(CustomHttpStatus.INVALID_POST.getCode())
				.body(new ApiResponse(2001, e.getMessage(), null));
	}

	// 게시글 내용 형식이 올바르지 않습니다.
	@ExceptionHandler(MissingPostFieldException.class)
	public ResponseEntity<ApiResponse> missingPostField(MissingPostFieldException e) {
		return ResponseEntity.status(CustomHttpStatus.INVALID_POST.getCode())
				.body(new ApiResponse(2002, e.getMessage(), null));
	}

	// 게시글 작성 권한 없음
	@ExceptionHandler(BoardWriteForbiddenException.class)
	public ResponseEntity<ApiResponse> boardWriteForbidden(BoardWriteForbiddenException e) {
		return ResponseEntity.status(CustomHttpStatus.BOARD_CUD_FORBIDDEN.getCode())
				.body(new ApiResponse(2003, e.getMessage(), null));
	}

	// 게시글 수정 권한 없음
	@ExceptionHandler(BoardUpdateForbiddenException.class)
	public ResponseEntity<ApiResponse> boardUpdateForbidden(BoardUpdateForbiddenException e) {
		return ResponseEntity.status(CustomHttpStatus.BOARD_CUD_FORBIDDEN.getCode())
				.body(new ApiResponse(2004, e.getMessage(), null));
	}

	// 게시글 삭제 권한 없음
	@ExceptionHandler(BoardDeleteForbiddenException.class)
	public ResponseEntity<ApiResponse> boardDeleteForbidden(BoardDeleteForbiddenException e) {
		return ResponseEntity.status(CustomHttpStatus.BOARD_CUD_FORBIDDEN.getCode())
				.body(new ApiResponse(2005, e.getMessage(), null));
	}

	// 이미 삭제된 게시글
	@ExceptionHandler(AlreadyDeletedBoardException.class)
	public ResponseEntity<ApiResponse> alreadyDeletedBoard(AlreadyDeletedBoardException e) {
		return ResponseEntity.status(CustomHttpStatus.ALREADY_DELETED_BOARD.getCode())
				.body(new ApiResponse(2006, e.getMessage(), null));
	}

	// 게시글 등록 실패
	@ExceptionHandler(BoardCreateException.class)
	public ResponseEntity<ApiResponse> boardCreateException(BoardCreateException e) {
		return ResponseEntity.status(CustomHttpStatus.BOARD_CRUD_FAILED.getCode())
				.body(new ApiResponse(2007, e.getMessage(), null));
	}

	// 게시글 수정 실패
	@ExceptionHandler(BoardUpdateException.class)
	public ResponseEntity<ApiResponse> boardUpdateException(BoardUpdateException e) {
		return ResponseEntity.status(CustomHttpStatus.BOARD_CRUD_FAILED.getCode())
				.body(new ApiResponse(2008, e.getMessage(), null));
	}

	// 게시글 삭제 실패
	@ExceptionHandler(BoardDeleteException.class)
	public ResponseEntity<ApiResponse> boardDeleteException(BoardDeleteException e) {
		return ResponseEntity.status(CustomHttpStatus.BOARD_CRUD_FAILED.getCode())
				.body(new ApiResponse(2009, e.getMessage(), null));
	}

	// 게시글 조회 실패
	@ExceptionHandler(BoardReadException.class)
	public ResponseEntity<ApiResponse> boardReadException(BoardReadException e) {
		return ResponseEntity.status(CustomHttpStatus.BOARD_CRUD_FAILED.getCode())
				.body(new ApiResponse(2010, e.getMessage(), null));
	}

	// ---------------------------------------------------------------------------------------------------
	// 파일이 존재하지 않음
	@ExceptionHandler(BoardFileNotFoundException.class)
	public ResponseEntity<ApiResponse> handleBoardFileNotFound(BoardFileNotFoundException e) {

		return ResponseEntity.status(CustomHttpStatus.BOARD_FILE_NOT_FOUND.getCode())
				.body(new ApiResponse(2100, e.getMessage(), null));
	}

	// 파일 형식 / 파일명 오류
	@ExceptionHandler(InvalidBoardFileFormatException.class)
	public ResponseEntity<ApiResponse> handleInvalidBoardFileFormat(InvalidBoardFileFormatException e) {

		return ResponseEntity.status(CustomHttpStatus.INVALID_BOARD_FILE.getCode())
				.body(new ApiResponse(2101, e.getMessage(), null));
	}

	// 파일 등록 실패
	@ExceptionHandler(BoardFileCreateException.class)
	public ResponseEntity<ApiResponse> handleBoardFileCreate(BoardFileCreateException e) {

		return ResponseEntity.status(CustomHttpStatus.BOARD_FILE_CRUD_FAILED.getCode())
				.body(new ApiResponse(2105, e.getMessage(), null));
	}

	// 파일 수정 실패
	@ExceptionHandler(BoardFileUpdateException.class)
	public ResponseEntity<ApiResponse> handleBoardFileUpdate(BoardFileUpdateException e) {

		return ResponseEntity.status(CustomHttpStatus.BOARD_FILE_CRUD_FAILED.getCode())
				.body(new ApiResponse(2106, e.getMessage(), null));
	}

	// 파일 삭제 실패
	@ExceptionHandler(BoardFileDeleteException.class)
	public ResponseEntity<ApiResponse> handleBoardFileDelete(BoardFileDeleteException e) {

		return ResponseEntity.status(CustomHttpStatus.BOARD_FILE_CRUD_FAILED.getCode())
				.body(new ApiResponse(2107, e.getMessage(), null));
	}

	// 파일 조회 실패
	@ExceptionHandler(BoardFileReadException.class)
	public ResponseEntity<ApiResponse> handleBoardFileRead(BoardFileReadException e) {

		return ResponseEntity.status(CustomHttpStatus.BOARD_FILE_CRUD_FAILED.getCode())
				.body(new ApiResponse(2108, e.getMessage(), null));
	}

	// --------------------------------------------------------------------------------------

	// 답변이 존재하지 않음 (조회 시 데이터 없음)
	@ExceptionHandler(AnswerNotFoundException.class)
	public ResponseEntity<ApiResponse> handleAnswerNotFound(AnswerNotFoundException e) {

		return ResponseEntity.status(CustomHttpStatus.ANSWER_NOT_FOUND.getCode())
				.body(new ApiResponse(3000, e.getMessage(), null));
	}

	// 답변 내용 형식 오류 (validation 실패)
	@ExceptionHandler(InvalidAnswerFormatException.class)
	public ResponseEntity<ApiResponse> handleInvalidAnswerFormat(InvalidAnswerFormatException e) {

		return ResponseEntity.status(CustomHttpStatus.INVALID_ANSWER.getCode())
				.body(new ApiResponse(3001, e.getMessage(), null));
	}

	// 답변 필수 입력값 누락 (null 또는 empty)
	@ExceptionHandler(MissingAnswerFieldException.class)
	public ResponseEntity<ApiResponse> handleMissingAnswerField(MissingAnswerFieldException e) {

		return ResponseEntity.status(CustomHttpStatus.MISSING_ANSWER_FIELD.getCode())
				.body(new ApiResponse(3002, e.getMessage(), null));
	}

	// 답변 등록 실패 (DB insert 실패 등 서버 오류)
	@ExceptionHandler(AnswerCreateException.class)
	public ResponseEntity<ApiResponse> handleAnswerCreate(AnswerCreateException e) {

		return ResponseEntity.status(CustomHttpStatus.ANSWER_CRUD_FAILED.getCode())
				.body(new ApiResponse(3004, e.getMessage(), null));
	}

	// 답변 수정 실패 (DB update 실패 등 서버 오류)
	@ExceptionHandler(AnswerUpdateException.class)
	public ResponseEntity<ApiResponse> handleAnswerUpdate(AnswerUpdateException e) {

		return ResponseEntity.status(CustomHttpStatus.ANSWER_CRUD_FAILED.getCode())
				.body(new ApiResponse(3005, e.getMessage(), null));
	}

	// 답변 삭제 실패 (DB delete 실패 등 서버 오류)
	@ExceptionHandler(AnswerDeleteException.class)
	public ResponseEntity<ApiResponse> handleAnswerDelete(AnswerDeleteException e) {

		return ResponseEntity.status(CustomHttpStatus.ANSWER_CRUD_FAILED.getCode())
				.body(new ApiResponse(3006, e.getMessage(), null));
	}

	// 답변 조회 실패 (DB select 실패 등 서버 오류)
	@ExceptionHandler(AnswerReadException.class)
	public ResponseEntity<ApiResponse> handleAnswerRead(AnswerReadException e) {

		return ResponseEntity.status(CustomHttpStatus.ANSWER_CRUD_FAILED.getCode())
				.body(new ApiResponse(3007, e.getMessage(), null));
	}

	// ---------------------------------------------------------------------------------------------
	// 충전소가 존재하지 않음 (조회 결과 없음)
	@ExceptionHandler(StationNotFoundException.class)
	public ResponseEntity<ApiResponse> handleStationNotFound(StationNotFoundException e) {

		return ResponseEntity.status(CustomHttpStatus.STATION_NOT_FOUND.getCode())
				.body(new ApiResponse(4000, e.getMessage(), null));
	}

	// 충전소 입력값 형식 오류 (이름/지역/주소/설명/위도/경도 validation 실패)
	@ExceptionHandler(InvalidStationFormatException.class)
	public ResponseEntity<ApiResponse> handleInvalidStationFormat(InvalidStationFormatException e) {

		return ResponseEntity.status(CustomHttpStatus.INVALID_STATION.getCode())
				.body(new ApiResponse(4001, e.getMessage(), null));
	}

	// 충전소 필수 입력값 누락 (null 또는 empty)
	@ExceptionHandler(MissingStationFieldException.class)
	public ResponseEntity<ApiResponse> handleMissingStationField(MissingStationFieldException e) {

		return ResponseEntity.status(CustomHttpStatus.MISSING_STATION_FIELD.getCode())
				.body(new ApiResponse(4002, e.getMessage(), null));
	}

	// 충전소 생성 실패 (DB insert 실패)
	@ExceptionHandler(StationCreateException.class)
	public ResponseEntity<ApiResponse> handleStationCreate(StationCreateException e) {

		return ResponseEntity.status(CustomHttpStatus.STATION_CRUD_FAILED.getCode())
				.body(new ApiResponse(4003, e.getMessage(), null));
	}

	// 충전소 수정 실패 (DB update 실패)
	@ExceptionHandler(StationUpdateException.class)
	public ResponseEntity<ApiResponse> handleStationUpdate(StationUpdateException e) {

		return ResponseEntity.status(CustomHttpStatus.STATION_CRUD_FAILED.getCode())
				.body(new ApiResponse(4004, e.getMessage(), null));
	}

	// 충전소 삭제 실패 (DB delete 실패)
	@ExceptionHandler(StationDeleteException.class)
	public ResponseEntity<ApiResponse> handleStationDelete(StationDeleteException e) {

		return ResponseEntity.status(CustomHttpStatus.STATION_CRUD_FAILED.getCode())
				.body(new ApiResponse(4005, e.getMessage(), null));
	}

	// 충전소 조회 실패 (DB select 실패)
	@ExceptionHandler(StationReadException.class)
	public ResponseEntity<ApiResponse> handleStationRead(StationReadException e) {

		return ResponseEntity.status(CustomHttpStatus.STATION_CRUD_FAILED.getCode())
				.body(new ApiResponse(4006, e.getMessage(), null));
	}

	// -----------------------------------------------------------------------------------------------

	// 충전기가 존재하지 않음 (조회 결과 없음)
	@ExceptionHandler(ChargerNotFoundException.class)
	public ResponseEntity<ApiResponse> handleChargerNotFound(ChargerNotFoundException e) {

		return ResponseEntity.status(CustomHttpStatus.CHARGER_NOT_FOUND.getCode())
				.body(new ApiResponse(5000, e.getMessage(), null));
	}

	// 충전기 입력값 형식 오류 (상태 / 충전소 번호 validation 실패)
	@ExceptionHandler(InvalidChargerFormatException.class)
	public ResponseEntity<ApiResponse> handleInvalidChargerFormat(InvalidChargerFormatException e) {

		return ResponseEntity.status(CustomHttpStatus.INVALID_CHARGER.getCode())
				.body(new ApiResponse(5001, e.getMessage(), null));
	}

	// 충전기 필수 입력값 누락 (null 또는 empty)
	@ExceptionHandler(MissingChargerFieldException.class)
	public ResponseEntity<ApiResponse> handleMissingChargerField(MissingChargerFieldException e) {

		return ResponseEntity.status(CustomHttpStatus.MISSING_CHARGER_FIELD.getCode())
				.body(new ApiResponse(5002, e.getMessage(), null));
	}

	// 충전기 등록 실패 (DB insert 실패)
	@ExceptionHandler(ChargerCreateException.class)
	public ResponseEntity<ApiResponse> handleChargerCreate(ChargerCreateException e) {

		return ResponseEntity.status(CustomHttpStatus.CHARGER_CRUD_FAILED.getCode())
				.body(new ApiResponse(5003, e.getMessage(), null));
	}

	// 충전기 수정 실패 (DB update 실패)
	@ExceptionHandler(ChargerUpdateException.class)
	public ResponseEntity<ApiResponse> handleChargerUpdate(ChargerUpdateException e) {

		return ResponseEntity.status(CustomHttpStatus.CHARGER_CRUD_FAILED.getCode())
				.body(new ApiResponse(5004, e.getMessage(), null));
	}

	// 충전기 삭제 실패 (DB delete 실패)
	@ExceptionHandler(ChargerDeleteException.class)
	public ResponseEntity<ApiResponse> handleChargerDelete(ChargerDeleteException e) {

		return ResponseEntity.status(CustomHttpStatus.CHARGER_CRUD_FAILED.getCode())
				.body(new ApiResponse(5005, e.getMessage(), null));
	}

	// 충전기 조회 실패 (DB select 실패)
	@ExceptionHandler(ChargerReadException.class)
	public ResponseEntity<ApiResponse> handleChargerRead(ChargerReadException e) {

		return ResponseEntity.status(CustomHttpStatus.CHARGER_CRUD_FAILED.getCode())
				.body(new ApiResponse(5006, e.getMessage(), null));
	}

	// 충전기가 이미 해당 충전소에 존재함 (중복 데이터)
	@ExceptionHandler(DuplicateChargerException.class)
	public ResponseEntity<ApiResponse> handleDuplicateCharger(DuplicateChargerException e) {

		return ResponseEntity.status(CustomHttpStatus.DUPLICATE_CHARGER.getCode())
				.body(new ApiResponse(5007, e.getMessage(), null));
	}

	// ----------------------------------------------------------------------------------------

	// 상품 조회때 일치하는 상품이 없을때
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ApiResponse> ProductNotFound(ProductNotFoundException e) {

		return ResponseEntity.status(CustomHttpStatus.PRODUCT_NOT_FOUND.getCode())
				.body(new ApiResponse(7000, e.getMessage(), null));
	}

	// 상품 구매때 상품 재고가 부족할 때
	@ExceptionHandler(InsufficientInventoryException.class)
	public ResponseEntity<ApiResponse> InsufficientInventory(InsufficientInventoryException e) {

		return ResponseEntity.status(CustomHttpStatus.INSUFFICIENT_RESOURCE.getCode())
				.body(new ApiResponse(7009, e.getMessage(), null));
	}

	// 인벤토리 업데이트 실패
	@ExceptionHandler(InventoryUpdateException.class)
	public ResponseEntity<ApiResponse> InventoryUpdate(InventoryUpdateException e) {

		return ResponseEntity.status(CustomHttpStatus.INVENTORY_CRUD_FAILED.getCode())
				.body(new ApiResponse(7017, e.getMessage(), null));
	}

	// 보유한 마일리지가 구매할 상품의 가격보다 적습니다.
	@ExceptionHandler(InsufficientMileageException.class)
	public ResponseEntity<ApiResponse> InsufficientMileage(InsufficientMileageException e) {

		return ResponseEntity.status(CustomHttpStatus.INSUFFICIENT_RESOURCE.getCode())
				.body(new ApiResponse(7011, e.getMessage(), null));
	}
	
	// 보유한 마일리지가 구매할 상품의 가격보다 적습니다.
		@ExceptionHandler(MileageHistoryNotFoundException.class)
		public ResponseEntity<ApiResponse> MileageHistoryNotFound(MileageHistoryNotFoundException e) {

			return ResponseEntity.status(CustomHttpStatus.MILEAGE_HISTORY_NOT_FOUND.getCode())
					.body(new ApiResponse(7010, e.getMessage(), null));
		}
	

}
