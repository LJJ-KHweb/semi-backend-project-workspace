package com.tri.evre.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.board.model.dao.BoardMapper;
import com.tri.evre.board.model.dto.BoardCreateRequest;
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
import com.tri.evre.global.exception.board.BoardReadException;
import com.tri.evre.global.exception.board.BoardUpdateException;
import com.tri.evre.notice.model.dto.NoticeDto;
import com.tri.evre.notice.model.service.NoticeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {

	private final BoardMapper boardMapper;
	private final FileManagementService fileService;
	private final NoticeService noticeService;
	
	//@Qualifier interface 구현체가 2개일때 어떤걸로 주입해줄지 결정해주는 에노테이션
	// 사용방법 RequiredArgsConstructor말고 직접 생성자 매개변수에 써서 주입해줘야됨
	public BoardService(BoardMapper boardMapper, NoticeService noticeService ,@Qualifier("boardFileService") FileManagementService fileService) {
		super();
		this.boardMapper = boardMapper;
		this.fileService = fileService;
		this.noticeService = noticeService;
	}
	
	// 게시글 작성
	@Transactional
	public void save(BoardCreateRequest board, List<MultipartFile> files, CustomUserDetails user) {

		Board boardEntity = Board.builder().boardTitle(board.getBoardTitle())
											.boardContent(board.getBoardContent())
											.userId(user.getUsername())
											.build();
		log.info("boardEntity : {}", boardEntity);
		
		int result = boardMapper.save(boardEntity);

		if (result < 1) {
			throw new BoardCreateException("게시글 등록 실패");
		}
		
		if (files != null && !files.isEmpty()) {
			fileService.saveFile(files, boardEntity.getBoardNo());
		}
		
		// 파일 까지 들어간 상태
		
		
		
	}
	
	
	//게시글 전체조회
	@Transactional
	public BoardListResponse findAll(PageInfo pageInfo) {
		List<BoardDto> boards = boardMapper.findAll(pageInfo);	
		if (boards.isEmpty()) {
			throw new BoardNotFoundException("조회 결과가 없습니다.");
		}
		pageInfo.setBoardCounts(boardMapper.findBoardsCount());
		List<NoticeDto> notices = noticeService.findPublicNotice();

		return BoardListResponse.builder()
								.pageInfo(pageInfo)
								.notices(notices)
								.boards(boards)
								.build();
	}

	
	//게시글 상세 조회
	@Transactional
	public BoardDto findByBoardNo(Long boardNo) {
		plusViews(boardNo);
		BoardDto board = boardMapper.findByBoardNo(boardNo);
		if (board == null) {
			throw new BoardNotFoundException("조회 결과가 없습니다.");
		}
		board.setFiles(fileService.findAll(boardNo));
		return board;
	}

	

	//게시글 수정
	@Transactional
	public void update(BoardDto board, List<MultipartFile> files, CustomUserDetails user) {
		validateNoticeExists(board.getBoardNo());
		Board boardEntity = Board.builder().boardNo(board.getBoardNo())
											.boardTitle(board.getBoardTitle())
											.boardContent(board.getBoardContent())
											.userId(user.getUsername())
											.build();
		int result = boardMapper.update(boardEntity);
		if (result < 1) {
			throw new BoardUpdateException("게시글 수정 실패");
		}
		fileService.updateFile(files, board.getDeleteOrder(), boardEntity.getBoardNo());
	}

	//게시글 삭제
	public void delete(Long boardNo, CustomUserDetails user) {
		validateNoticeExists(boardNo);
		BoardDeleteDto board = BoardDeleteDto.builder().boardNo(boardNo)
														.userId(user.getUsername())
														.build();
		int result = boardMapper.delete(board);
		if(result < 1) {
			throw new BoardDeleteException("게시글 삭제 실패");
		}
	}
	
	
	//조회수 증가 책임분리함
	private void plusViews(Long boardNo) {
		int result = boardMapper.plusViews(boardNo);
		if(result < 1) {
			throw new BoardUpdateException("조회수 증가에 실패했습니다.");
		}
	}
	
	//해당 번호의 게시글의 존재 여부를 확인하는 메소드 
	private void validateNoticeExists(Long boardNo) {
		if(boardMapper.existsByBoardNo(boardNo) != 1) {
			throw new BoardReadException("해당 번호의 공지사항이 존재하지 않습니다.");
		}
	}

}
