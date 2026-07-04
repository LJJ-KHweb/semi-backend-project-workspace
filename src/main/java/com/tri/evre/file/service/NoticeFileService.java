package com.tri.evre.file.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.file.model.dao.FileMapper;
import com.tri.evre.file.model.dto.FileDto;
import com.tri.evre.file.model.vo.NoticeFile;
import com.tri.evre.global.exception.board.file.BoardFileCreateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeFileService implements FileManagementService {

	private final FileStorageService fileStorageService;
	private final FileMapper fileMapper;


	@Override
	public void saveFile(List<MultipartFile> files, Long noticeNo) {
		if(files == null) {
			return;
		}
		int count = 1;
		for(MultipartFile file : files) {
			if(count >5) {
				throw new BoardFileCreateException("파일이 너무 많습니다.");
			}
			String filePath = fileStorageService.store(file);
			NoticeFile fileEntity = NoticeFile.builder()
									.filePath(filePath)
									.fileOrder(count)
									.originalName(file.getOriginalFilename())
									.noticeNo(noticeNo)
									.build();
			saveNoticeFile(fileEntity);
			count++;
		}
	}

	@Override
	public List<FileDto> findAll(Long noticeNo) {
		countNoticeFiles(noticeNo);
		return findNoticeFiles(noticeNo);
	
	}

	

	@Override
	public void updateFile(List<MultipartFile> files, Long noticeNo) {
		int result = 0;
		if(files == null) {
			return;
		}
		
		int boardFileCounts = countNoticeFiles(noticeNo);
		
		int count = 1;
		for(MultipartFile file : files) {
			if(count >5) {
				throw new BoardFileCreateException("파일이 너무 많습니다.");
			}
			String filePath = fileStorageService.store(file);
			NoticeFile fileEntity = NoticeFile.builder()
								.filePath(filePath)
								.fileOrder(count)
								.originalName(file.getOriginalFilename())
								.noticeNo(noticeNo)
								.build();
			updateNoticeFile(fileEntity);
			
			count++;
		}
		// 한 게시글의 저장되어있는 파일의 수가 업데이트의 파일수보다 크다면 더있는 파일들을 삭제해줌
		if(boardFileCounts > (count-1)) {
			deleteNoticeFiles(noticeNo, count-1);
		}
	}
	
	
	//공지사항 테이블에 저장해주는 메소드 
	private void saveNoticeFile(NoticeFile file) {
		int result = fileMapper.saveNoticeFile(file);
		if(result < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
	}
	
	// 공지사항 테이블에 update를 해주는 메소드
	private void updateNoticeFile(NoticeFile file) {
		int result = fileMapper.updateNoticeFile(file);
		if(result < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
	}
	
	// 공지사항 테이블에 delete를 해주는 메소드
	private void deleteNoticeFiles(Long boardNo, int maxOrder) {
		int result = fileMapper.deleteNoticeFile(boardNo, maxOrder);
		if(result < 1) {
			throw new BoardFileCreateException("파일 삭제에 실패했습니다.");
		}
	}
	
	// 공지사항 테이블에 file이 있는지 없는지 검증
	private int  countNoticeFiles(Long boardNo) {
		int boardFileCounts =fileMapper.findNoticeFileCounts(boardNo);
		if(boardFileCounts < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
		return boardFileCounts;
	}
	// 공지사항 테이블에 file 전체조회
	private List<FileDto> findNoticeFiles(Long boardNo) {
		List<FileDto> files= fileMapper.findNoticeFileAll(boardNo);
		if(files.isEmpty()) {
			throw new BoardFileCreateException("파일을 조회하지 못했습니다.");
		}
		return files;
		
	}
}
