package com.tri.evre.board.model.dto;

import java.sql.Date;
import java.util.List;

import com.tri.evre.file.model.dto.FileDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequest {
	private Long boardNo;
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s]{2,30}$", message = "제목의 형식이 잘못되었습니다.")
	private String boardTitle;
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s]{2,500}$", message = "내용의 형식이 잘못되었습니다.")
	private String boardContent;
	// 차량 정보 같이 insert해줄 변수
	private Date startTime;
	private Date finishTime;
	private String carNo;
}
