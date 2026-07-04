package com.tri.evre.file.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.file.model.dao.FileMapper;
import com.tri.evre.file.model.dto.FileDto;
import com.tri.evre.file.model.vo.BoardFile;
import com.tri.evre.global.exception.board.file.BoardFileCreateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardFileService implements FileManagementService {

	private final FileStorageService fileStorageService;
	private final FileMapper fileMapper;



	@Override
	public void saveFile(List<MultipartFile> files, Long boardNo) {
		if(files == null) {
			return;
		}
		int count = 1;
		for(MultipartFile file : files) {
			if(count >5) {
				throw new BoardFileCreateException("파일이 너무 많습니다.");
			}
			String filePath = fileStorageService.store(file);
			BoardFile fileEntity = BoardFile.builder()
								.filePath(filePath)
								.fileOrder(count)
								.originalName(file.getOriginalFilename())
								.boardNo(boardNo)
								.build();
			saveBoardFile(fileEntity);
			count++;
		}
	}

	@Override
	public List<FileDto> findAll(Long boardNo) {
		countBoardFiles(boardNo);
		return findBoardFiles(boardNo);
	
	}

	

	@Override
	public void updateFile(List<MultipartFile> files, Long boardNo) {
		int result = 0;
		if(files == null) {
			return;
		}
		
		int boardFileCounts = countBoardFiles(boardNo);
		
		int count = 1;
		for(MultipartFile file : files) {
			if(count >5) {
				throw new BoardFileCreateException("파일이 너무 많습니다.");
			}
			String filePath = fileStorageService.store(file);
			BoardFile fileEntity = BoardFile.builder()
								.filePath(filePath)
								.fileOrder(count)
								.originalName(file.getOriginalFilename())
								.boardNo(boardNo)
								.build();
			updateBoardFile(fileEntity);
			
			count++;
		}
		// 한 게시글의 저장되어있는 파일의 수가 업데이트의 파일수보다 크다면 더있는 파일들을 삭제해줌
		if(boardFileCounts > (count-1)) {
			deleteBoardFiles(boardNo, count-1);
		}
	}
	
	
	// Board 게시글의 BoardType에 맞는 테이블에 저장해주는 메소드 
	private void saveBoardFile(BoardFile file) {
		int result = fileMapper.saveBoardFile(file);
		
		if(result < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
	}
	
	// Board 게시글의 update를 해주는 메소드
	private void updateBoardFile(BoardFile file) {
		int result = fileMapper.updateBoardFile(file);
		
		if(result < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
	}
	
	// Board 게시글의 delete를 해주는 메소드
	private void deleteBoardFiles(Long boardNo, int maxOrder) {
		int result =  fileMapper.deleteBoardFile(boardNo, maxOrder);
			
		if(result < 1) {
			throw new BoardFileCreateException("파일 삭제에 실패했습니다.");
		}
	}
	
	// Board 게시글의 file이 있는지 없는지 검증
	private int  countBoardFiles(Long boardNo) {
		int boardFileCounts =  fileMapper.findBoardFileCounts(boardNo);
		
		if(boardFileCounts < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
		return boardFileCounts;
	}
	// Board 게시글의 파일 전체 조회
	private List<FileDto> findBoardFiles(Long boardNo) {
		List<FileDto> files= fileMapper.findBoardFileAll(boardNo);
			
		if(files.isEmpty()) {
			throw new BoardFileCreateException("파일을 조회하지 못했습니다.");
		}
		return files;
		
	}
}
