package com.tri.evre.require.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.file.model.dto.RequireListResponse;
import com.tri.evre.file.service.FileManagementService;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.board.BoardCreateException;
import com.tri.evre.global.exception.board.BoardNotFoundException;
import com.tri.evre.require.model.dao.RequireMapper;
import com.tri.evre.require.model.dto.RequireDto;
import com.tri.evre.require.model.vo.Require;
import com.tri.evre.require.model.vo.RequireResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RequireService {
	private final RequireMapper requireMapper;
	private final FileManagementService fileService;
	
	
	public RequireService(RequireMapper requireMapper, @Qualifier("requireFileService") FileManagementService fileService) {
		super();
		this.requireMapper = requireMapper;
		this.fileService = fileService;
	}
	
	@Transactional
	public void writeRequire(RequireDto require, List<MultipartFile> files, CustomUserDetails user) {
		
		Require requireEntity = Require.builder()
									   .requireTitle(require.getRequireTitle())
									   .requireContent(require.getRequireContent())
									   .userId(user.getUsername())
									   .build();
		
		int result = requireMapper.wirteRequire(requireEntity);
		
		if(result < 1) {
			throw new BoardCreateException("문의사항 작성에 실패했습니다.");
		}
		
		if (files != null && !files.isEmpty()) {
		    fileService.saveFile(files, requireEntity.getRequireNo());
		}
	}

	
	// 
	public RequireListResponse findAll(PageInfo pageInfo, String user) {
		List<RequireResponse> requires = requireMapper.findAll(pageInfo, user);
		
		if (requires.isEmpty()) {
			throw new BoardNotFoundException("조회 결과가 없습니다.");
		}
		
		pageInfo.setBoardCounts(requireMapper.findRequiresCount());
		
		return RequireListResponse.builder()
								  .pageInfo(pageInfo)
								  .requires(requires)
								  .build();
		
	}


}
