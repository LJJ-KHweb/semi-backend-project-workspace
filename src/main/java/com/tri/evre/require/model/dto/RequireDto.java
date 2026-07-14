package com.tri.evre.require.model.dto;

import java.sql.Date;
import java.util.List;

import com.tri.evre.file.model.dto.FileDto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequireDto {
	@Pattern(regexp="[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s.,!?()/\\-]{2,20}", message="제목의 형식이 잘못되었습니다.")
	private String requireTitle;
	@Pattern(regexp="[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s.,!?()/\\-]{2,200}", message="제목의 형식이 잘못되었습니다.")
	private String requireContent;
	private String userId;
	
	private List<FileDto> files;
	private Date createDate;
	private Long views;
}
