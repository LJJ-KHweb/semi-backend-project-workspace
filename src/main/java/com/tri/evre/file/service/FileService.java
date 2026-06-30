package com.tri.evre.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.file.model.dto.FileDto;

public interface FileService {
	String store(MultipartFile file);

	void saveFile(List<MultipartFile> files, Long boardNo);

	List<FileDto> findAll(Long boardNo);

	void updateFile(List<MultipartFile> files, Long boardNo);

}
