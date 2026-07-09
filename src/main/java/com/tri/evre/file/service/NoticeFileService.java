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
		if(files.isEmpty()) {
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
		return findNoticeFiles(noticeNo);
	
	}

	

	@Override
	public void updateFile(List<MultipartFile> files, List<Integer> deleteOrder, Long noticeNo) {
		if (deleteOrder != null && !deleteOrder.isEmpty()) {
			for(Integer order : deleteOrder) {
				fileMapper.deleteNoticeFile(noticeNo, order);
			}
		}
		
		int maxOrder = fileMapper.findNoticeFileCounts(noticeNo);
		int count = maxOrder+1;
		if(files != null&& !files.isEmpty()) {
			if(files.size() > 0) {
				for(MultipartFile file : files) {
					String filePath = fileStorageService.store(file);
					NoticeFile fileEntity = NoticeFile.builder()
							.filePath(filePath)
							.fileOrder(count++)
							.originalName(file.getOriginalFilename())
							.noticeNo(noticeNo)
							.build();
					saveNoticeFile(fileEntity);
				}
			}
		}
	
	}
	//공지사항 테이블에 저장해주는 메소드 
	private void saveNoticeFile(NoticeFile file) {
		int result = fileMapper.saveNoticeFile(file);
		if(result < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
	}
	
	
	// 공지사항 테이블에 file이 있는지 없는지 검증
	private int  countNoticeFiles(Long noticeNo) {
		int noticeFileCounts =fileMapper.findNoticeFileCounts(noticeNo);
		if(noticeFileCounts < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
		return noticeFileCounts;
	}
	// 공지사항 테이블에 file 전체조회
	private List<FileDto> findNoticeFiles(Long noticeNo) {
		List<FileDto> files= fileMapper.findNoticeFileAll(noticeNo);
		return files;
		// 예외 처리 안한이유 파일이 없을수도 있음
		
	}
	
	public void deleteFile(Long noticeNo) {
		countNoticeFiles(noticeNo);
		int result = fileMapper.deleteFile(noticeNo);
		if(result < 1) {
			throw new BoardFileCreateException("파일 삭제 실패했습니다.");
		}
	}
}
