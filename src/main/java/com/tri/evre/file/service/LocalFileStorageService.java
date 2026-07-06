package com.tri.evre.file.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocalFileStorageService implements FileStorageService {
	
	private final Path fileLocation;
	
	public LocalFileStorageService() {
		this.fileLocation = Paths.get("uploads").toAbsolutePath().normalize();
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
			return "http://localhost/uploads/" + changeName;
			/* "http://192.168.51.4/uploads/" + changeName; */
		} catch (IOException e) {
			throw new RuntimeException("이상한 파일입니다.");
		}
	}

}
