package com.tri.evre.notice.model.dto;

import java.sql.Date;
import java.util.List;

import com.tri.evre.file.model.dto.FileDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {
	private Long noticeNo;
	@Pattern(regexp = "[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s.,!?()/\\-]{2,30}", message = "제목의 형식이 잘못되었습니다.")
	private String noticeTitle;
	@Pattern(regexp = "[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s.,!?:~()/'\"\\-]{2,500}", message = "형식이 잘못되었습니다.")
	private String noticeContent;

	private Date createDate;
	private Long views;
	// 공개범위를 어디로 할지 정해주는 필드임
	@Pattern(regexp = "^[YN]$", message = "Y 또는 N만 입력 가능합니다.")
	private String publicYN;
	private String status;
	private String userId;

	private List<Integer> deleteOrder;

	private String userName;
	private List<FileDto> files;
}
