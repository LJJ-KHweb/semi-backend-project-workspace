package com.tri.evre.require.model.dto;

import java.sql.Date;
import java.util.List;

import com.tri.evre.file.model.dto.FileDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequireDto {
	private String requireTitle;
	private String requireContent;
	
	
	private List<FileDto> files;
	private Date createDate;
	private Long views;
}
