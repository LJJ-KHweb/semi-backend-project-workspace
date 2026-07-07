package com.tri.evre.require.model.dto;

import java.sql.Date;
import java.util.List;

import com.tri.evre.file.model.dto.FileDto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdminRequireDto {
	private String requireTitle;
	private String requireContent;
	private String userId;
	
	private List<FileDto> files;
	private Date createDate;
	private Long views;
}
