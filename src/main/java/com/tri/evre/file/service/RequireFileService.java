package com.tri.evre.file.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.file.model.dao.FileMapper;
import com.tri.evre.file.model.dto.FileDto;
import com.tri.evre.file.model.vo.RequireFile;
import com.tri.evre.global.exception.board.file.BoardFileCreateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequireFileService implements FileManagementService {

	private final FileStorageService fileStorageService;
	private final FileMapper fileMapper;


	@Override
	public void saveFile(List<MultipartFile> files, Long requireNo) {
		if(files.isEmpty()) {
			return;
		}
		int count = 1;
		for(MultipartFile file : files) {
			if(count >5) {
				throw new BoardFileCreateException("파일이 너무 많습니다.");
			}
			String filePath = fileStorageService.store(file);
			RequireFile fileEntity = RequireFile.builder()
									.filePath(filePath)
									.fileOrder(count)
									.originalName(file.getOriginalFilename())
									.requireNo(requireNo)
									.build();
			saveRequireFile(fileEntity);
			count++;
		}
	}

	@Override
	public List<FileDto> findAll(Long requireNo) {
		return findRequireFiles(requireNo);
	
	}

	@Override
	public void updateFile(List<MultipartFile> files, Long requireNo) {
		int result = 0;
		if(files.isEmpty()) {
			return;
		}
		
		int requireFileCounts = countRequireFiles(requireNo);
		
		int count = 1;
		for(MultipartFile file : files) {
			if(count >5) {
				throw new BoardFileCreateException("파일이 너무 많습니다.");
			}
			String filePath = fileStorageService.store(file);
			RequireFile fileEntity = RequireFile.builder()
								.filePath(filePath)
								.fileOrder(count)
								.originalName(file.getOriginalFilename())
								.requireNo(requireNo)
								.build();
			updateRequireFile(fileEntity);
			
			count++;
		}
		// 한 게시글의 저장되어있는 파일의 수가 업데이트의 파일수보다 크다면 더있는 파일들을 삭제해줌
		if(requireFileCounts > (count-1)) {
			deleteRequireFiles(requireNo, count-1);
		}
	}
	
	
	//공지사항 테이블에 저장해주는 메소드 
	private void saveRequireFile(RequireFile file) {
		int result = fileMapper.saveRequireFile(file);
		if(result < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
	}
	
	// 공지사항 테이블에 update를 해주는 메소드
	private void updateRequireFile(RequireFile file) {
		int result = fileMapper.updateRequireFile(file);
		if(result < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
	}
	
	// 공지사항 테이블에 delete를 해주는 메소드
	private void deleteRequireFiles(Long boardNo, int maxOrder) {
		int result = fileMapper.deleteRequireFile(boardNo, maxOrder);
		if(result < 1) {
			throw new BoardFileCreateException("파일 삭제에 실패했습니다.");
		}
	}
	
	// 공지사항 테이블에 file이 있는지 없는지 검증
	private int  countRequireFiles(Long boardNo) {
		int RequireFileCounts =fileMapper.findRequireFileCounts(boardNo);
		if(RequireFileCounts < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
		return RequireFileCounts;
	}
	// 공지사항 테이블에 file 전체조회
	private List<FileDto> findRequireFiles(Long boardNo) {
		List<FileDto> files= fileMapper.findRequireFileAll(boardNo);
		return files;
		// 예외 처리 안한이유 파일이 없을수도 있음
		
	}
	
	public void deleteFile(Long boardNo) {
		countRequireFiles(boardNo);
		int result = fileMapper.deleteFile(boardNo);
		if(result < 1) {
			throw new BoardFileCreateException("파일 삭제 실패했습니다.");
		}
	}
}
