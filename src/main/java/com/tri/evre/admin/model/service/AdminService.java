package com.tri.evre.admin.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.admin.model.vo.AdminPage;
import com.tri.evre.answer.model.dao.AnswerMapper;
import com.tri.evre.answer.model.vo.Answer;
import com.tri.evre.board.model.dao.BoardMapper;
import com.tri.evre.board.model.dto.BoardDeleteDto;
import com.tri.evre.board.model.dto.BoardDto;
import com.tri.evre.board.model.dto.BoardListResponse;
import com.tri.evre.charger.model.dao.ChargerMapper;
import com.tri.evre.charger.model.dto.ChargerDto;
import com.tri.evre.charger.model.dto.ChargerRequest;
import com.tri.evre.charger.model.dto.ChargerResponse;
import com.tri.evre.charger.model.vo.Charger;
import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.file.model.dao.FileMapper;
import com.tri.evre.file.model.dto.FileDto;
import com.tri.evre.file.model.dto.RequireListResponseAdmin;
import com.tri.evre.file.service.FileStorageService;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.board.BoardCreateException;
import com.tri.evre.global.exception.board.BoardDeleteException;
import com.tri.evre.global.exception.board.BoardNotFoundException;
import com.tri.evre.global.exception.charger.ChargerNotFoundException;
import com.tri.evre.global.exception.charger.ChargerReadException;
import com.tri.evre.global.exception.product.ProductCreateException;
import com.tri.evre.global.exception.product.ProductDeleteFailException;
import com.tri.evre.global.exception.shop.ProductNotFoundException;
import com.tri.evre.global.exception.station.StationDeleteException;
import com.tri.evre.global.exception.station.StationNotFoundException;
import com.tri.evre.global.exception.station.StationReadException;
import com.tri.evre.global.exception.user.UserNotFoundException;
import com.tri.evre.product.model.dao.ProductMapper;
import com.tri.evre.product.model.dto.ProductDto;
import com.tri.evre.product.model.dto.ProductListDto;
import com.tri.evre.product.model.dto.UpdateProductDto;
import com.tri.evre.product.model.vo.Product;
import com.tri.evre.require.model.dao.RequireMapper;
import com.tri.evre.require.model.vo.Require;
import com.tri.evre.shop.model.dao.ShopMapper;
import com.tri.evre.shop.model.dto.ProductListResponse;
import com.tri.evre.shop.model.dto.PurchaseProductDto;
import com.tri.evre.shop.model.dto.PurchaseResponseDto;
import com.tri.evre.shop.model.dto.WeeklyProductPurchaseDto;
import com.tri.evre.station.model.dao.StationMapper;
import com.tri.evre.station.model.dto.SearchInfo;
import com.tri.evre.station.model.dto.StationDto;
import com.tri.evre.station.model.dto.StationSearchRequest;
import com.tri.evre.station.model.vo.Station;
import com.tri.evre.user.model.dao.UserMapper;
import com.tri.evre.user.model.dto.AllUserResponseDto;
import com.tri.evre.user.model.dto.UserDto;
import com.tri.evre.user.model.dto.UserMaskedDto;
import com.tri.evre.user.model.dto.UserRoleRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

	private final BoardMapper boardMapper;
	private final ShopMapper shopMapper;
	private final StationMapper stationMapper;
	// ---- 07/02 선겸--
	private final ProductMapper productMapper;
	private final FileStorageService fileService;
	private final UserMapper userMapper;

	// == 07/06 김선겸
	private final RequireMapper requireMapper;

	// 07/07 김선겸
	private final AnswerMapper answerMapper;

	// -- 07/06 심영도 --
	private final ChargerMapper chargerMapper;

	// 07/09
	private final FileMapper fileMapper;

	@Transactional(readOnly = true)
	public BoardListResponse findAll(PageInfo pageInfo) {

		pageInfo.setBoardCounts(boardMapper.findAllBoardsCount());

		List<BoardDto> boards = boardMapper.adminFindAll(pageInfo);

		if (boards.isEmpty()) {
			throw new BoardNotFoundException("전체 게시글 조회 실패");
		}

		return new BoardListResponse(pageInfo, boards);
	}

	@Transactional(readOnly = true)
	public BoardDto findByBoard(Long boardNo) {

		BoardDto board = boardMapper.findByBoardNo(boardNo);
		if (board == null) {
			throw new BoardNotFoundException("게시글 정보가 없습니다.");
		}

		List<FileDto> files = fileMapper.findBoardFileAll(boardNo);

		board.setFiles(files);

		return board;
	}

	@Transactional
	public void deleteBoard(Long boardNo, CustomUserDetails user) {

		BoardDeleteDto board = new BoardDeleteDto(boardNo, user.getUsername(), user.getRole());

		int result = boardMapper.delete(board);
		if (result < 1) {
			throw new BoardDeleteException("게시글 삭제 실패");
		}
	}

	@Transactional(readOnly = true)
	public ProductListResponse findAllProduct(PageInfo pageInfo) {

		List<ProductListDto> products = shopMapper.findAllProductAdmin(pageInfo);

		// 테이블에 아무것도 없을때
		if (products.isEmpty()) {
			throw new ProductNotFoundException("상품을 하나도 찾을 수 없습니다.");
		}
		pageInfo.setBoardCounts(shopMapper.findProductCounts());

		return new ProductListResponse(pageInfo, products);
	}

	// -------------------------------07/02
	@Transactional
	public void insertProduct(CustomUserDetails user, ProductDto product, MultipartFile file) {

		Product productEntity = Product.builder().userId(user.getUsername()).productName(product.getProductName())
				.price(product.getPrice()).amount(product.getAmount()).build();

		int result = productMapper.insertProductTable(productEntity);
		if (result < 1) {
			throw new ProductCreateException("상품 등록에 실패");
		}

		String filePath = fileService.store(file);

		result = productMapper.insertInventoryTable(productEntity, filePath);
		if (result < 1) {
			throw new ProductCreateException("상품 수량 등록 실패");
		}

	}

	// ------------------ 07/03 김선겸
	// --- 상품 삭제
	@Transactional
	public void deleteProduct(Long productNo) {
		ProductDto product = shopMapper.findByProductNo(productNo);
		if (product == null)
			throw new ProductNotFoundException("존재하지 않는 상품입니다.");

		if (product.getStatus().equals("N"))
			throw new ProductNotFoundException("이미 삭제된 상품입니다.");

		int result = productMapper.deleteProduct(productNo);
		if (result < 1) {
			throw new ProductDeleteFailException("삭제에 실패했습니다.");
		}
	}

	// 상품 수정
	@Transactional
	public void updateProduct(Long productNo, UpdateProductDto product, MultipartFile file) {

		// 앞단을 생각 못하고 뒷단만 생각하다보니 값을 입력하지 않은 필드가 있을 경우 udpate실행 안함
		// 원래 앞단에서 확인하는 건데 여기다 만들어버렸음
		// 수정할 때 앞단에서 상품의 정보를 가지고 있을 것이고 앞단에서 이미 있는 정보와 업데이트시 보내는 정보를 비교해서 값이 넘어올것이다.
		boolean isProductUpdate = (product.getProductName() != null && !product.getProductName().isBlank())
				|| product.getPrice() != null;
		int result = 0;
		if (isProductUpdate) {
			result = productMapper.updateProduct(productNo, product);
			if (result < 1) {
				throw new ProductCreateException("상품 수정에 실패했습니다.");
			}
		}

		String filePath = (file != null && !file.isEmpty()) ? fileService.store(file) : null;

		boolean isInventoryUpdate = product.getAmount() != null || filePath != null;

		if (isInventoryUpdate) {
			result = productMapper.updateInventory(productNo, product.getAmount(), filePath);

			if (result < 1) {
				throw new ProductCreateException("상품 재고 수정에 실패했습니다.");
			}
		}
	}

	// ---07/02 이재준-----------------------------------------------------
	@Transactional(readOnly = true)
	public PurchaseResponseDto findAllPurchaseProduct(PageInfo pageInfo) {
		List<PurchaseProductDto> rankings = shopMapper.findAllPurchaseProduct(pageInfo);
		if (rankings.isEmpty()) {
			throw new ProductNotFoundException("사용가 상품 구매 랭킹 조회 실패했습니다.");
		}
		pageInfo.setBoardCounts(shopMapper.findAllProductCounts());
		return PurchaseResponseDto.builder().ranks(rankings)
											.pageInfo(pageInfo)
											.build();
	}

	@Transactional(readOnly = true)
	public List<WeeklyProductPurchaseDto> findByPurchaseCount() {
		List<WeeklyProductPurchaseDto> weeklyPurchaseList = shopMapper.findByPurchaseCount();
		if (weeklyPurchaseList.isEmpty()) {
			throw new ProductNotFoundException("요일별 상품 구매수량 조회 실패했습니다.");
		}
		return weeklyPurchaseList;
	}

	// -----------------07/03 심영도 충전소 전체 조회ㅋㅋ
	@Transactional
	public StationSearchRequest findAllStations(PageInfo pageInfo) {

		pageInfo.setBoardCounts(stationMapper.findAllStationCount());
		if (pageInfo.getBoardCounts() < 1) {
			throw new StationNotFoundException("충전소가 없습니다.");
		}

		List<StationDto> stations = stationMapper.findAllStation(pageInfo);

		for (StationDto station : stations) {
			int chargerCount = stationMapper.findChargerCount(station.getStationNo());
			station.setChargerCount(chargerCount);
			int unableChargers = stationMapper.findUnableCharger(station.getStationNo());
			station.setUnableChargerCount(unableChargers);
		}

		StationSearchRequest searchResponse = new StationSearchRequest(pageInfo, stations);

		return searchResponse;
	}

	@Transactional
	public void insertStation(StationDto station) {
		Station stationEntity = Station.builder().stationName(station.getStationName())
				.stationDesc(station.getStationDesc()).region(station.getRegion()).address(station.getAddress())
				.chargerCount(station.getChargerCount()).lat(station.getLat()).lng(station.getLng()).build();

		SearchInfo stationInfo = new SearchInfo(station.getLat(), station.getLng());

		int duplicateStation = stationMapper.checkDuplicate(stationInfo);
		if (duplicateStation > 0) {
			throw new StationReadException("이미 존재하는 충전소입니다.");
		}

		int result = stationMapper.insertStation(stationEntity);
		if(result < 1) {
		    throw new StationReadException("충전소 등록에 실패했습니다.");
		}

		for (int i = 0; i < stationEntity.getChargerCount(); i++) {
			chargerMapper.insertCharger(stationEntity.getStationNo());
		}

	}

	// 07/04 심영도 충전소 상세보기
	@Transactional
	public StationDto findByStationNo(Long stationNo) {

		StationDto station = stationMapper.findByAdminStationNo(stationNo);
		if (station == null) {
			throw new StationReadException("충전소 조회에 실패했습니다.");
		}

		int chargerCount = stationMapper.findChargerCount(station.getStationNo());
		if (chargerCount < 0) {
			throw new ChargerReadException("충전기 조회에 실패했습니다.");
		}

		int unableChargers = stationMapper.findUnableCharger(station.getStationNo());
		station.setChargerCount(chargerCount);
		station.setUnableChargerCount(unableChargers);

		return station;
	}

	// 07/04 충전소 수정
	@Transactional
	public void updateStation(Long stationNo, StationDto station) {
		Station stationEntity = Station.builder()
									   .stationNo(stationNo)
									   .stationName(station.getStationName())
									   .stationDesc(station.getStationDesc())
									   .region(station.getRegion())
									   .address(station.getAddress())
									   .lat(station.getLat())
									   .lng(station.getLng())
									   .status(station.getStatus())
									   .build();

		StationDto stationDto = stationMapper.findByAdminStationNo(stationNo);
		if (stationDto == null) {
			throw new StationNotFoundException("일치하는 충전소가 없습니다.");
		}

		StationDto stationInfo = new StationDto(stationNo, stationEntity.getLat(), stationEntity.getLng());
		log.info("stationNo: {}", stationInfo);
		int duplicateStation = stationMapper.checkDuplicateByNo(stationInfo);
		if (duplicateStation > 0) {
			throw new StationReadException("이미 존재하는 충전소입니다.");
		}

		int result = stationMapper.updateStation(stationEntity);

		if(result < 1) {
		    throw new StationReadException("충전소 수정에 실패했습니다.");
		}
	}

	@Transactional
	public AllUserResponseDto findAllUser(PageInfo pageInfo, String role) {

		List<UserDto> users = userMapper.findAllUser(pageInfo, role);
		pageInfo.setBoardCounts(userMapper.findAllUserCounts());
		return AllUserResponseDto.builder().pageInfo(pageInfo).users(maskingUser(users)).build();

	}

	@Transactional
	public void updateUserRole(UserRoleRequestDto user) {

		int userCount = userMapper.countByUserId(user.getUserId());
		if (userCount < 1) {
			throw new UserNotFoundException("일치하는 회원이 없습니다.");
		}
		int result = userMapper.updateUserRole(user);

		if(result < 1) {
		    throw new UserNotFoundException("회원 권한 수정에 실패했습니다.");
		}

	}

	// 07/06 심영도 충전소 삭제
	@Transactional
	public void deleteStation(Long stationNo) {

		if (stationMapper.findByStationNo(stationNo) == null) {
			throw new StationNotFoundException("충전소를 찾을 수 없습니다.");
		}

		if (stationMapper.deleteStation(stationNo) < 1) {
			throw new StationDeleteException("충전소 삭제에 실패했습니다.");
		}
	}

	// 7.6 심영도 충전기 전체 조회
	@Transactional(readOnly = true)
	public ChargerResponse findAllCharger(PageInfo pageInfo) {

		pageInfo.setBoardCounts(chargerMapper.findAllChargerCount());
		if (pageInfo.getBoardCounts() < 1) {
			throw new StationNotFoundException("충전기가 없습니다.");
		}

		List<ChargerDto> chargers = chargerMapper.findAllCharger(pageInfo);

		ChargerResponse chargerResponse = new ChargerResponse(pageInfo, chargers);

		return chargerResponse;

	}

	// === 07/06 김선겸 문의사항 전체 조회
	@Transactional(readOnly = true)
	public RequireListResponseAdmin findAllRequires(PageInfo pageInfo) {

		pageInfo.setBoardCounts(requireMapper.findAllRequireCounts());

		List<Require> boards = requireMapper.adminFindAllRequires(pageInfo);

		if (boards.isEmpty()) {
			throw new BoardNotFoundException("전체 문의사항 조회 실패");
		}

		return new RequireListResponseAdmin(pageInfo, boards);
	}

	@Transactional
	public void insertCharger(Long stationNo) {

		StationDto station = findByStationNo(stationNo);
		if (station == null) {
			throw new StationNotFoundException("충전소를 찾을 수 없습니다.");
		}
		if (station.getStatus().equals("N")) {
			throw new StationNotFoundException("충전소가 운영 중이지 않습니다.");
		}

		int result = chargerMapper.insertCharger(stationNo);

		if(result < 1) {
		    throw new ChargerReadException("충전기 등록에 실패했습니다.");
		}
	}

	// 7.7 심영도 충전기 단일 조회(예외처리용)
	@Transactional(readOnly = true)
	private ChargerDto findByChargerNo(Long chargerNo) { // 예외처리라서 COUNT(*)로 int 형만 받아오려고 했는데 삭제할때 staionNo 필요해서 Long으로
															// 받음
		ChargerDto charger = chargerMapper.findByChargerNo(chargerNo);
		// Long인데 sql에서 chargerNo로 조회된 값이 없으면 null 반환해요 그래서 null 이면 예외를 터트린다고 한 겁니다.(0 이
		// 올 수가 없음)
		if (charger == null) {
			throw new ChargerNotFoundException("일치하는 충전기를 찾을 수 없습니다.");
		}
		return charger;
	}

	// 7.7 심영도 삭제된 충전소 찾기
	@Transactional(readOnly = true)
	private void validateStation(Long stationNo) {
		if (findByStationNo(stationNo) == null) {
			throw new StationNotFoundException("일치하는 충전소가 없습니다.");
		}
	}

	// 7.7 심영도 충전기 업데이트
	@Transactional
	public void updateCharger(Long chargerNo, ChargerDto charger) {

		findByChargerNo(chargerNo);

		Charger chargerEntity = Charger.builder().chargerNo(chargerNo).status(charger.getStatus())
				.stationNo(charger.getStationNo()).build();

		validateStation(chargerEntity.getStationNo());

		int result = chargerMapper.updateCharger(chargerEntity);

		if(result < 1) {
		    throw new ChargerReadException("충전기 수정에 실패했습니다.");
		}

	}

	// 문의사항 응답하기
	@Transactional
	public void insertAnswer(Answer answer) {

		int result = answerMapper.insertAnswer(answer);

		if (result < 1) {
			throw new BoardCreateException("문의사항 응답 작성에 실패했습니다.");
		}

	}

	// 7.7 심영도 충전소 삭제
	@Transactional
	public void deleteCharger(Long chargerNo) {
		ChargerDto charger = findByChargerNo(chargerNo);
		validateStation(charger.getStationNo());
		chargerMapper.deleteCharger(chargerNo);
	}

	@Transactional
	public AdminPage adminPage() {

		int total = sumRequires();
		int finish = finishRequires();
		int notFinish = total - finish;

		AdminPage adminPage = AdminPage.builder().sumRequires(total).finishRequires(finish).notFinishRequires(notFinish)
				.sumUsers(sumUsers()).build();
		return adminPage;
	}

	public int sumRequires() {
		return requireMapper.sumRequires();
	}

	public int finishRequires() {
		return requireMapper.finishRequires();
	}

	public int sumUsers() {
		return userMapper.sumUsers();
	}

	@Transactional
	public void restoreProduct(Long productNo) {
		int result = shopMapper.restoreProduct(productNo);

		if(result < 1) {
		    throw new ProductCreateException("상품 복구에 실패했습니다.");
		}

	}

	@Transactional
	private List<UserMaskedDto> maskingUser(List<UserDto> users) {
		List<UserMaskedDto> userList = new ArrayList<>();
		for (UserDto user : users) {
			userList.add(UserMaskedDto.builder().userId(user.getUserId()).userName(user.getUserName())
					.email(user.getEmail()).role(user.getRole()).createDate(user.getCreateDate())
					.originalUserId(user.getUserId()).build());
		}
		return userList;
	}

	// 7.10 심영도 충전소번호로 충전기 조회
	@Transactional(readOnly = true)
	public ChargerResponse findChargerByStationNo(Long stationNo, PageInfo pageInfo) {
		pageInfo.setBoardCounts(chargerMapper.stationChargerCount(stationNo));
		if (pageInfo.getBoardCounts() < 1) {
			throw new StationNotFoundException("충전기가 없습니다.");
		}

		ChargerRequest chargerRequest = new ChargerRequest(pageInfo, stationNo);

		List<ChargerDto> chargers = chargerMapper.findChargerByStationNo(chargerRequest);

		return new ChargerResponse(pageInfo, chargers);
	}

}
