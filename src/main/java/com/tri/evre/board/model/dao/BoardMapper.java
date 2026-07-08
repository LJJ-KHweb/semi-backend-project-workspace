package com.tri.evre.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tri.evre.board.model.dto.BoardDeleteDto;
import com.tri.evre.board.model.dto.BoardDto;
import com.tri.evre.board.model.vo.Board;
import com.tri.evre.common.model.dto.PageInfo;

@Mapper
public interface BoardMapper {
	
	//게시글 작성
	int save(Board boardEntity);
	
	//게시글 전체 조회
	List<BoardDto> findAll(PageInfo pageInfo);
	
	//게시글 상세 조회
	BoardDto findByBoardNo(Long boardNo);
	
	//게시글 수정
	int update(Board board);

	//게시글 삭제
	int delete(BoardDeleteDto board);

	//게시글 전체 갯수 조회
	int findBoardsCount();

	//게시글 조회수 증가
	int plusViews(Long boardNo);

	//게시글 삭제 포함 조회
	List<BoardDto> adminFindAll(PageInfo pageInfo);

	//게시글 삭제 포함 전체 갯수 조회
	int findAllBoardsCount();

	//게시글의 존재 여부 확인
	int existsByBoardNo(Long boardNo);


	
}
