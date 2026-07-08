package com.tri.evre.board.model.dto;

import java.sql.Date;
import java.util.List;

import com.tri.evre.file.model.dto.FileDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
	private Long boardNo;
	@Pattern(regexp="[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s.,!?()/\\-]{2,200}", message="제목의 형식이 잘못되었습니다.")
	private String boardTitle;
	@Pattern(regexp="^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s.,!?()/\\-]{2,200}$", message="내용의 형식이 잘못되었습니다.")
	private String boardContent;
	private String userName;
	private String userId;
	// 추가 6/30 재준
	private String status;
	
	private List<FileDto> existingFiles;
	private List<Integer> deleteOrder;
	
	
	//--------------------
	private List<FileDto> files;
	private Date createDate;
	private Long views;
}
