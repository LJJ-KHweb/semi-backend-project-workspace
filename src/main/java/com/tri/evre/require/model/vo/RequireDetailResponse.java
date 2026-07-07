package com.tri.evre.require.model.vo;

import java.sql.Date;
import java.util.List;

import com.tri.evre.answer.model.vo.ResponseAnswer;
import com.tri.evre.file.model.dto.FileDto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RequireDetailResponse {
	private String requireTitle;
	private String requireContent;
	
	
	private List<FileDto> files;
	private Date createDate;
	
	private List<ResponseAnswer> answer;
}
