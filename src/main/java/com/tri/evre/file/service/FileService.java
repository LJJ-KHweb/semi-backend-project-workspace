package com.tri.evre.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	String store(MultipartFile file);

}
