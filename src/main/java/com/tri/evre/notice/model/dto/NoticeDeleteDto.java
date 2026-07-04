package com.tri.evre.notice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDeleteDto {
	private Long noticeNo;
	private String userId;
	private String role;
}
