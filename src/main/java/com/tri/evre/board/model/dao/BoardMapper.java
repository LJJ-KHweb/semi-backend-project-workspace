package com.tri.evre.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tri.evre.board.model.dto.BoardDto;
import com.tri.evre.board.model.vo.Board;
import com.tri.evre.common.model.dto.PageInfo;

@Mapper
public interface BoardMapper {
	
	
	List<BoardDto> findAll(PageInfo pageInfo);

	
	int findBoardsCount();


	BoardDto findByBoardNo(Long boardNo);


	int plusViews(Long boardNo);
	
	
	int save(Board boardEntity);


	int update(Board boardEntity);


	int delete(Board board);

	
	
}
