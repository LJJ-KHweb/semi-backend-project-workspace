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
import com.tri.evre.file.model.vo.File;
import com.tri.evre.global.exception.board.file.BoardFileCreateException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocalFileService implements FileService {

	private final Path fileLocation;
	private final FileMapper fileMapper;

	public LocalFileService(FileMapper fileMapper) {
		this.fileLocation = Paths.get("uploads").toAbsolutePath().normalize();
		this.fileMapper = fileMapper;
	}

	@Override
	public String store(MultipartFile file) {
		// 이름 바꾸기 생략
		String originalFileName = file.getOriginalFilename();
		

		// 원본 파일 확장자 뽑기
		String ext = originalFileName.substring(originalFileName.lastIndexOf("."));

		// 2. 년월일시분초
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		// 난수 뽑기
		int randNum = (int) (Math.random() * 900) + 100;

		String changeName = "EVRE_" + currentTime + "_" + randNum + ext;
		
		Path targetLocation = this.fileLocation.resolve(changeName);
		
		try {
			
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return "http://192.168.51.4/uploads/" + changeName;
		} catch (IOException e) {
			throw new RuntimeException("이상한 파일입니다.");
		}
	}

	@Override
	public void saveFile(List<MultipartFile> files, Long boardNo) {
		int result = 0;
		if(files == null) {
			return;
		}
		int count = 1;
		for(MultipartFile file : files) {
			if(count >5) {
				throw new BoardFileCreateException("파일이 너무 많습니다.");
			}
			String filePath = store(file);
			File fileEntity = File.builder()
								.filePath(filePath)
								.fileOrder(count)
								.originalName(file.getOriginalFilename())
								.boardNo(boardNo)
								.build();
			
			result = fileMapper.save(fileEntity);
			if(result < 1) {
				throw new BoardFileCreateException("파일이 이상합니다.");
			}
			count++;
		}
	}

	@Override
	public List<FileDto> findAll(Long boardNo) {
		
		return fileMapper.findAll(boardNo);
	
	}

	@Override
	public void updateFile(List<MultipartFile> files, Long boardNo) {
		int result = 0;
		if(files == null) {
			return;
		}
		
		int fileCount = fileMapper.fileCounts(boardNo);
		
		int count = 1;
		for(MultipartFile file : files) {
			if(count >5) {
				throw new BoardFileCreateException("파일이 너무 많습니다.");
			}
			String filePath = store(file);
			File fileEntity = File.builder()
								.filePath(filePath)
								.fileOrder(count)
								.originalName(file.getOriginalFilename())
								.boardNo(boardNo)
								.build();
			
			result = fileMapper.update(fileEntity);
			if(result < 1) {
				throw new BoardFileCreateException("파일이 이상합니다.");
			}
			count++;
		}
		if(fileCount > (count-1)) {
			fileMapper.deleteFile(boardNo, count-1);
		}
	}
	
	

}
