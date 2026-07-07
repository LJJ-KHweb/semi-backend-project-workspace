package com.tri.evre.require.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.answer.model.dao.AnswerMapper;
import com.tri.evre.answer.model.vo.ResponseAnswer;
import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.file.model.dto.RequireListResponse;
import com.tri.evre.file.service.FileManagementService;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.board.BoardCreateException;
import com.tri.evre.global.exception.board.BoardNotFoundException;
import com.tri.evre.require.model.dao.RequireMapper;
import com.tri.evre.require.model.dto.RequireDto;
import com.tri.evre.require.model.vo.Require;
import com.tri.evre.require.model.vo.RequireDetailResponse;
import com.tri.evre.require.model.vo.RequireResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RequireService {
	private final RequireMapper requireMapper;
	private final FileManagementService fileService;
	
	private final AnswerMapper answerMapper;
	
	public RequireService(RequireMapper requireMapper, @Qualifier("requireFileService") FileManagementService fileService, AnswerMapper answerMapper) {
		super();
		this.requireMapper = requireMapper;
		this.fileService = fileService;
		this.answerMapper = answerMapper;
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

	// 문의사항 상세보기
	public RequireDetailResponse findByRequireNo(Long requireNo, String userId) {
		RequireDto require = requireMapper.findByBoardNo(requireNo, userId);
		if (require == null) {
			throw new BoardNotFoundException("조회 결과가 없습니다.");
		}
		
		require.setFiles(fileService.findAll(requireNo));
		
		List<ResponseAnswer> answers = answerMapper.findAllAnswerByRequireNo(requireNo);
		
		
		RequireDetailResponse response = RequireDetailResponse.builder()
															  .requireTitle(require.getRequireTitle())
															  .requireContent(require.getRequireContent())
															  .files(require.getFiles())
															  .createDate(require.getCreateDate())
															  .answer(answers)
															  .build();
		
		return response;
	}
	// (관리자)문의사항 상세보기
	public RequireDetailResponse findByRequireNoAdmin(Long requireNo) {
		RequireDto require = requireMapper.findByRequireNoAdmin(requireNo);
		if (require == null) {
			throw new BoardNotFoundException("조회 결과가 없습니다.");
		}
		
		require.setFiles(fileService.findAll(requireNo));
		
		List<ResponseAnswer> answers = answerMapper.findAllAnswerByRequireNo(requireNo);
		
		
		RequireDetailResponse response = RequireDetailResponse.builder()
															  .requireTitle(require.getRequireTitle())
															  .requireContent(require.getRequireContent())
															  .userId(require.getUserId())
															  .files(require.getFiles())
															  .createDate(require.getCreateDate())
															  .answer(answers)
															  .build();
		
		return response;
	}




}
