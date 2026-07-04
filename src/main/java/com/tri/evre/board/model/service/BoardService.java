package com.tri.evre.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.board.model.dao.BoardMapper;
import com.tri.evre.board.model.dto.BoardDeleteDto;
import com.tri.evre.board.model.dto.BoardDto;
import com.tri.evre.board.model.dto.BoardListResponse;
import com.tri.evre.board.model.vo.Board;
import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.file.service.FileManagementService;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.board.BoardCreateException;
import com.tri.evre.global.exception.board.BoardDeleteException;
import com.tri.evre.global.exception.board.BoardNotFoundException;
import com.tri.evre.global.exception.board.BoardUpdateException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {

	private final BoardMapper boardMapper;
	private final FileManagementService fileService;
	
	//@Qualifier interface 구현체가 2개일때 어떤걸로 주입해줄지 결정해주는 에노테이션
	// 사용방법 RequiredArgsConstructor말고 직접 생성자 매개변수에 써서 주입해줘야됨
	public BoardService(BoardMapper boardMapper, @Qualifier("boardFileService")  FileManagementService fileService) {
		super();
		this.boardMapper = boardMapper;
		this.fileService = fileService;
	}
	
	

	@Transactional
	public BoardListResponse findAll(int page) {

		PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(page);
		pageInfo.setSize(2);
		pageInfo.setOffset(page * 2);
		List<BoardDto> boards = boardMapper.findAll(pageInfo);	
		if (boards == null) {
			throw new BoardNotFoundException("조회 결과가 없습니다.");
		}
		pageInfo.setBoardCounts(boardMapper.findBoardsCount());
		if (pageInfo.getBoardCounts() < 1) {
			throw new BoardNotFoundException("조회 결과가 없습니다.");
		}

		log.info("{}", boards);
		BoardListResponse boardResponse = new BoardListResponse();
		boardResponse.setBoards(boards);
		boardResponse.setPageInfo(pageInfo);
		return boardResponse;
	}

	@Transactional
	public BoardDto findByBoardNo(Long boardNo) {

		BoardDto board = boardMapper.findByBoardNo(boardNo);

		if (board == null) {
			throw new BoardNotFoundException("조회 결과가 없습니다.");
		}

		int result = boardMapper.plusViews(boardNo);
		if (result < 1) {
			throw new BoardUpdateException("조회수 증가 업데이트 실패");
		}
		board.setFiles(fileService.findAll(boardNo));
		return board;
	}

	@Transactional
	public void save(BoardDto board, List<MultipartFile> files, CustomUserDetails user) {

		Board boardEntity = Board.builder().boardTitle(board.getBoardTitle())
											.boardContent(board.getBoardContent())
											.userId(user.getUsername())
											.build();

		int result = boardMapper.save(boardEntity);

		if (result < 1) {
			throw new BoardCreateException("게시글 등록 실패");
		}

		fileService.saveFile(files, boardEntity.getBoardNo());
		
	}

	public void update(BoardDto board, List<MultipartFile> files, CustomUserDetails user) {
		Board boardEntity = Board.builder().boardNo(board.getBoardNo())
											.boardTitle(board.getBoardTitle())
											.boardContent(board.getBoardContent())
											.userId(user.getUsername())
											.build();

		int result = boardMapper.update(boardEntity);

		if (result < 1) {
			throw new BoardUpdateException("게시글 수정 실패");
		}

		fileService.updateFile(files, boardEntity.getBoardNo());
		
		
	}

	public void delete(Long boardNo, CustomUserDetails user) {
		findByBoardNo(boardNo);
		
		BoardDeleteDto board = new BoardDeleteDto();
		board.setBoardNo(boardNo);
		board.setUserId(user.getUsername());
		
		int result = boardMapper.delete(board);
		
		if(result < 1) {
			throw new BoardDeleteException("게시글 삭제 실패");
		}
	}



}
