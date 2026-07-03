package com.tri.evre.admin.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tri.evre.board.model.dao.BoardMapper;
import com.tri.evre.board.model.dto.BoardDeleteDto;
import com.tri.evre.board.model.dto.BoardDto;
import com.tri.evre.board.model.dto.BoardListResponse;
import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.board.BoardDeleteException;
import com.tri.evre.global.exception.board.BoardNotFoundException;
import com.tri.evre.global.exception.shop.ProductNotFoundException;
import com.tri.evre.shop.model.dao.ShopMapper;
import com.tri.evre.shop.model.dto.ProductListDto;
import com.tri.evre.shop.model.dto.ProductListResponse;
import com.tri.evre.station.model.dao.StationMapper;
import com.tri.evre.station.model.dto.StationDto;
import com.tri.evre.station.model.dto.StationSearchRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

	private final BoardMapper boardMapper;
	private final ShopMapper shopMapper;
	private final StationMapper stationMapper;
	
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
		
		List<ProductListDto> products = shopMapper.findAllProduct(pageInfo);
		
		
		// 테이블에 아무것도 없을때
		if(products==null || products.isEmpty()) {
			throw new ProductNotFoundException("상품을 하나도 찾을 수 없습니다.");
		}
		
		return new ProductListResponse(pageInfo, products);
	}

	
	// -----------------07/03 심영도 충전소 전체 조회ㅋㅋ
	public StationSearchRequest findAllStations(PageInfo pageInfo, double lat, double lng, int dist) {
		
		List<StationDto> stations = stationMapper.findAllStations(pageInfo, lat, lng, dist);
		
		return null;
	} 

	

}
