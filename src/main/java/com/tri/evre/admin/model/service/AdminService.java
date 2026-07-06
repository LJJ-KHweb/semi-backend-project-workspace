package com.tri.evre.admin.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.board.model.dao.BoardMapper;
import com.tri.evre.board.model.dto.BoardDeleteDto;
import com.tri.evre.board.model.dto.BoardDto;
import com.tri.evre.board.model.dto.BoardListResponse;
import com.tri.evre.charger.model.dao.ChargerMapper;
import com.tri.evre.charger.model.dto.ChargerDto;
import com.tri.evre.charger.model.dto.ChargerResponse;
import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.file.service.FileStorageService;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.board.BoardDeleteException;
import com.tri.evre.global.exception.board.BoardNotFoundException;
import com.tri.evre.global.exception.charger.ChargerCreateException;
import com.tri.evre.global.exception.charger.ChargerReadException;
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
import com.tri.evre.shop.model.dao.ShopMapper;
import com.tri.evre.shop.model.dto.ProductListResponse;
import com.tri.evre.shop.model.dto.PurchaseProductDto;
import com.tri.evre.shop.model.dto.WeeklyProductPurchaseDto;
import com.tri.evre.station.model.dao.StationMapper;
import com.tri.evre.station.model.dto.SearchInfo;
import com.tri.evre.station.model.dto.StationDto;
import com.tri.evre.station.model.dto.StationSearchRequest;
import com.tri.evre.station.model.vo.Station;
import com.tri.evre.user.model.dao.UserMapper;
import com.tri.evre.user.model.dto.UserDto;
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
	//---- 07/02 선겸--
	private final ProductMapper productMapper;
	private final FileStorageService fileService;
	private final UserMapper userMapper;
	
	//-- 07/06 심영도 --
	private final ChargerMapper chargerMapper;
	
	@Transactional
	public BoardListResponse findAll(PageInfo pageInfo) {
		
		pageInfo.setBoardCounts(boardMapper.findAllBoardsCount());
		
		List<BoardDto> boards = boardMapper.adminFindAll(pageInfo);
		
		if(boards == null) {
			throw new BoardNotFoundException("전체 게시글 조회 실패");
		}
		
		return new BoardListResponse(pageInfo,boards);
	}

	@Transactional
	public BoardDto findByBoard(Long boardNo) {
		
		BoardDto board = boardMapper.findByBoardNo(boardNo);
		if(board == null) {
			throw new BoardNotFoundException("게시글 정보가 없습니다.");
		}
		return board;
	}

	public void deleteBoard(Long boardNo,CustomUserDetails user) {
		
		BoardDeleteDto board = new BoardDeleteDto(boardNo, user.getUsername(), user.getRole());
		
		
		int result = boardMapper.delete(board);
		if(result < 1) {
			throw new BoardDeleteException("게시글 삭제 실패");
		}
	}

	
	//------------------07/01 김선겸---------
	public ProductListResponse findAllProduct(PageInfo pageInfo) {
		
		List<ProductListDto> products = shopMapper.findAllProductAdmin(pageInfo);
		
		
		// 테이블에 아무것도 없을때
		if(products==null || products.isEmpty()) {
			throw new ProductNotFoundException("상품을 하나도 찾을 수 없습니다.");
		}
		
		return new ProductListResponse(pageInfo, products);
	}

	//-------------------------------07/02
	@Transactional
	public void insertProduct(CustomUserDetails user, ProductDto product, MultipartFile file) {
		
		Product productEntity = Product.builder()
									   .userId(user.getUsername())
									   .productName(product.getProductName())
									   .price(product.getPrice())
									   .amount(product.getAmount())
									   .build();
		
		productMapper.insertProductTable(productEntity);
		
		String filePath =  fileService.store(file);
		
		productMapper.insertInventoryTable(productEntity, filePath);
		
		
	} 
	// ------------------ 07/03 김선겸
	// --- 상품 삭제
	@Transactional
	public void deleteProduct(Long productNo) {
		
		if(shopMapper.findByProductNo(productNo) == null) throw new ProductNotFoundException("존재하지 않는 상품입니다.");
		
		if(shopMapper.findByProductNo(productNo).getStatus().equals("N")) throw new ProductNotFoundException("이미 삭제된 상품입니다.");
		
		productMapper.deleteProduct(productNo);
	} 
	
	
	
	// 상품 수정
	@Transactional
	public void updateProduct(Long productNo, UpdateProductDto product, MultipartFile file) {
		
		
		// 앞단을 생각 못하고 뒷단만 생각하다보니 값을 입력하지 않은 필드가 있을 경우 udpate실행 안함
		// 원래 앞단에서 확인하는 건데 여기다 만들어버렸음
		// 수정할 때 앞단에서 상품의 정보를 가지고 있을 것이고 앞단에서 이미 있는 정보와 업데이트시 보내는 정보를 비교해서 값이 넘어올것이다.
	    boolean isProductUpdate =
	            (product.getProductName() != null && !product.getProductName().isBlank())
	            || product.getPrice() != null;

	    if (isProductUpdate) {
	        productMapper.updateProduct(productNo, product);
	    }

	    String filePath = (file != null && !file.isEmpty())
	            ? fileService.store(file)
	            : null;
	    
	    
	    boolean isInventoryUpdate =
	            product.getAmount() != null
	            || filePath != null;
	    
	    log.info("================{} {} {}", productNo, product.getAmount(), filePath);
	    
	    if (isInventoryUpdate) {
	        productMapper.updateInventory(productNo, product.getAmount(), filePath);
	    }
	}
	
	
	
	

	// ---07/02 이재준-----------------------------------------------------
	public List<PurchaseProductDto> findAllPurchaseProduct() {
		List<PurchaseProductDto> rankings = shopMapper.findAllPurchaseProduct();
		if(rankings.isEmpty()) {
			throw new ProductNotFoundException("사용가 상품 구매 랭킹 조회 실패했습니다.");
		}
		return rankings;
	}

	@Transactional
	public List<WeeklyProductPurchaseDto> findByPurchaseCount() {
		List<WeeklyProductPurchaseDto>  weeklyPurchaseList = shopMapper.findByPurchaseCount();
		if(weeklyPurchaseList.isEmpty()) {
			throw new ProductNotFoundException("요일별 상품 구매수량 조회 실패했습니다.");
		}
		return weeklyPurchaseList;
	}
	
	

	
	
	
	// -----------------07/03 심영도 충전소 전체 조회ㅋㅋ
		@Transactional
		public StationSearchRequest findAllStations(PageInfo pageInfo) {
			
			pageInfo.setBoardCounts(stationMapper.findAllStationCount());
			if(pageInfo.getBoardCounts() < 1) {
				throw new StationNotFoundException("충전소가 없습니다.");
			}
			
			List<StationDto> stations = stationMapper.findAllStation(pageInfo);
			
			for(StationDto station : stations) {
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
			Station stationEntity = Station.builder()
										   .stationName(station.getStationName())
										   .stationDesc(station.getStationDesc())
										   .region(station.getRegion())
										   .address(station.getAddress())
										   .lat(station.getLat())
										   .lng(station.getLng())
										   .build();
			
			SearchInfo stationInfo = new SearchInfo(station.getLat(), station.getLng());
			
			int duplicateStation = stationMapper.checkDuplicate(stationInfo);
			if(duplicateStation > 0) {
				throw new StationReadException("충전소가 중복입니다.");
			}
			
			stationMapper.insertStation(stationEntity);
		}

		// 07/04 심영도 충전소 상세보기
		@Transactional
		public StationDto findByStationNo(Long stationNo) {
			
			StationDto station = stationMapper.findByStationNo(stationNo);
			if(station == null) {
				throw new StationReadException("충전소 조회에 실패했습니다.");
			}
			
			int chargerCount = stationMapper.findChargerCount(station.getStationNo()); 
			if(chargerCount < 0) {
				throw new ChargerReadException("충전기 조회에 실패했습니다.");
			}
			
			int unableChargers = stationMapper.findUnableCharger(station.getStationNo());
			station.setChargerCount(chargerCount);
			station.setUnableChargerCount(unableChargers);
			
			return station;
		}

		// 07/04 충전소 수정
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
			
			StationDto stationDto = stationMapper.findByStationNo(stationNo);
			if(stationDto == null) {
				throw new StationNotFoundException("일치하는 충전소가 없습니다.");
			}
			
			StationDto stationInfo = new StationDto(station.getStationNo(), station.getLat(), station.getLng());
			int duplicateStation = stationMapper.checkDuplicateByNo(stationInfo);
			if(duplicateStation > 0) {
				throw new StationReadException("이미 존재하는 충전소입니다.");
			}
			
			stationMapper.updateStation(stationEntity);
		}

		
		@Transactional
		public List<UserDto> findAllUser(PageInfo pageInfo) {
			
			return userMapper.findAllUser(pageInfo);
		}

		
		
		public void updateUserRole(UserRoleRequestDto user) {
			
			int userCount  = userMapper.countByUserId(user.getUserId());
			if(userCount  < 1) {
				throw new  UserNotFoundException("일치하는 회원이 없습니다.");
			}
			
			userMapper.updateUserRole(user);
			
		} 
		
		//07/06 심영도 충전소 삭제
		public void deleteStation(Long stationNo) {
			
			if(stationMapper.findByStationNo(stationNo) == null) {
				throw new StationNotFoundException("충전소를 찾을 수 없습니다.");
			}
			
			if(stationMapper.deleteStation(stationNo) < 1) {
				throw new StationDeleteException("충전소 삭제에 실패했습니다.");
			}
		}

		public ChargerResponse findAllCharger(PageInfo pageInfo) {
			
			pageInfo.setBoardCounts(chargerMapper.findAllChargerCount());
			if(pageInfo.getBoardCounts() < 1) {
				throw new StationNotFoundException("충전기가 없습니다.");
			}
			
			List<ChargerDto> chargers = chargerMapper.findAllCharger(pageInfo);
			
			ChargerResponse chargerResponse = new ChargerResponse(pageInfo, chargers);
			
			return chargerResponse;
			
		}

		public void insertCharger(Long stationNo) {

			StationDto station = findByStationNo(stationNo);
			if(station == null) {
				throw new StationNotFoundException("충전소를 찾을 수 없습니다.");
			}
			if(station.getStatus() == "N") {
				throw new ChargerCreateException("충전소가 운영 중이지 않습니다.");
			}
			
			
			chargerMapper.insertCharger(stationNo);
		}

}
