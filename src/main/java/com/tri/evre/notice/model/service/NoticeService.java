package com.tri.evre.notice.model.service;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.file.model.dto.FileDto;
import com.tri.evre.file.service.FileManagementService;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.board.BoardCreateException;
import com.tri.evre.global.exception.board.BoardDeleteException;
import com.tri.evre.global.exception.board.BoardReadException;
import com.tri.evre.global.exception.board.BoardUpdateException;
import com.tri.evre.notice.model.dao.NoticeMapper;
import com.tri.evre.notice.model.dto.NoticeDeleteDto;
import com.tri.evre.notice.model.dto.NoticeDto;
import com.tri.evre.notice.model.dto.NoticeListResponse;
import com.tri.evre.notice.model.vo.Notice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoticeService {
	private final FileManagementService fileService;
	private final NoticeMapper noticeMapper;

	// @Qualifier interface 구현체가 2개일때 어떤걸로 주입해줄지 결정해주는 에노테이션
	// 사용방법 RequiredArgsConstructor말고 직접 생성자 매개변수에 써서 주입해줘야됨
	public NoticeService(@Qualifier("noticeFileService") FileManagementService fileService, NoticeMapper noticeMapper) {
		this.fileService = fileService;
		this.noticeMapper = noticeMapper;
	}

	// 공지사항 작성
	@Transactional
	public void save(NoticeDto notice, CustomUserDetails user, List<MultipartFile> files) {
		Notice noticeEntity = Notice.builder().noticeTitle(notice.getNoticeTitle())
											.noticeContent(notice.getNoticeContent())
											.userId(user.getUsername())
											.publicYN(notice.getPublicYN())
											.build();
		
		int result = noticeMapper.save(noticeEntity);
		if (result < 1) {
			throw new BoardCreateException("게시글 등록 실패했습니다.");
		}
		if (files != null && !files.isEmpty()) {
			fileService.saveFile(files, noticeEntity.getNoticeNo());
		}
	}

	// 공지사항 전체 조회
	@Transactional
	public NoticeListResponse findAll(PageInfo pageInfo) {
		List<NoticeDto> notices = noticeMapper.findAll(pageInfo);
		if (notices.isEmpty()) {
			throw new BoardReadException("일치하는 공지사항게시글이 없습니다");
		}
		pageInfo.setBoardCounts(noticeMapper.findNoticesCount());
		
		
		return NoticeListResponse.builder().pageInfo(pageInfo)
											.notices(notices)
											.build();
	}
	
	
	public NoticeListResponse findAllAdmin(PageInfo pageInfo) {
		List<NoticeDto> notices = noticeMapper.findAllAdmin(pageInfo);
		if (notices.isEmpty()) {
			throw new BoardReadException("일치하는 공지사항게시글이 없습니다");
		}
		pageInfo.setBoardCounts(noticeMapper.findNoticesCount());
		
		
		return NoticeListResponse.builder().pageInfo(pageInfo)
											.notices(notices)
											.build();
	}
	
	

	// 공지사항 상세 조회
	@Transactional
	public NoticeDto findByNoticeNo(Long noticeNo) {
		NoticeDto notice = noticeMapper.findByNoticeNo(noticeNo);
		plusViews(noticeNo);
		if(notice == null) {
			throw new BoardReadException("해당번호의 공지사항 게시글이 존재하지 않습니다.");
		}
		notice.setFiles(fileService.findAll(noticeNo));
		return notice;
	}
	
	@Transactional
	public NoticeDto findByNoticeNoAdmin(Long noticeNo) {
		NoticeDto notice = noticeMapper.findByNoticeNoAdmin(noticeNo);
		if(notice == null) {
			throw new BoardReadException("해당번호의 공지사항 게시글이 존재하지 않습니다.");
		}
		notice.setFiles(fileService.findAll(noticeNo));
		return notice;
	}

	
	
	
	
	
	//공지사항 publicYN = Y (일반게시판에서도 볼수 있는 공지사항)
	@Transactional
	public List<NoticeDto> findPublicNotice(){
		return noticeMapper.findPublicNotice();
		//예외 처리를 안하는 이유는 publicYN = Y인 공지사항이 없어도
		//일반게시판을 보여줘야 되기 때문에 예외처리 안함
	}

	// 공지사항 수정
	@Transactional
	public void update(NoticeDto notice, List<MultipartFile> files, CustomUserDetails user) {
		validateNoticeExists(notice.getNoticeNo());
		Notice noticeEntity = Notice.builder().noticeNo(notice.getNoticeNo())
												.noticeTitle(notice.getNoticeTitle())
												.noticeContent(notice.getNoticeContent())
												.userId(user.getUsername())
												.publicYN(notice.getPublicYN())
												.build();
		int result = noticeMapper.update(noticeEntity);
		if (result < 1) {
			throw new BoardUpdateException("게시글 수정 실패했습니다.");
		}			
		fileService.updateFile(files, notice.getDeleteOrder(), noticeEntity.getNoticeNo());
	}

	// 공지사항 삭제
	@Transactional
	public void delete(Long noticeNo, CustomUserDetails user) {
		validateNoticeExists(noticeNo);
		List<FileDto> files = fileService.findAll(noticeNo);
		if(files != null && !files.isEmpty()) {
			fileService.deleteFile(noticeNo);			
		}
		NoticeDeleteDto notice = NoticeDeleteDto.builder().noticeNo(noticeNo)
															.userId(user.getUsername())
															.build();
		int result = noticeMapper.delete(notice);
		if(result < 1) {
			throw new BoardDeleteException("공지사항 삭제 실패했습니다.");
		}
	}
	
	//조회수 증가 책임분리함
	private void plusViews(Long noticeNo) {
		int result = noticeMapper.plusViews(noticeNo);
		if(result < 1) {
			throw new BoardUpdateException("조회수 증가에 실패했습니다.");
		}
	}
	
	// 해당 번호의 공지사항의 존재 여부 확인하는 메소드 
	private void validateNoticeExists(Long noticeNo) {
		if(noticeMapper.existsByNoticeNo(noticeNo) == null) {
			throw new BoardReadException("해당 번호의 공지사항이 존재하지 않습니다.");
		}
	}
	

}
