package com.tri.evre.notice.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.file.service.FileManagementService;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.board.BoardNotFoundException;
import com.tri.evre.notice.model.dao.NoticeMapper;
import com.tri.evre.notice.model.dto.NoticeDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoticeService {
	private final FileManagementService fileService;
	private final NoticeMapper noticeMapper;

	//@Qualifier interface 구현체가 2개일때 어떤걸로 주입해줄지 결정해주는 에노테이션
	// 사용방법 RequiredArgsConstructor말고 직접 생성자 매개변수에 써서 주입해줘야됨
	public NoticeService(@Qualifier("noticeFileService") FileManagementService fileService, NoticeMapper noticeMapper) {
		this.fileService = fileService;
		this.noticeMapper = noticeMapper;
	}


	
	
	public List<NoticeDto> findAll(PageInfo pageInfo) {
		List<NoticeDto> notices = noticeMapper.findAll(pageInfo);
		if (notices.isEmpty()) {
			throw new BoardNotFoundException("일치하는 공지사항게시글이 없습니다");
		}
		return null;
	}

	public void save(NoticeDto notice, CustomUserDetails user, MultipartFile file) {
		
	}

	

}
