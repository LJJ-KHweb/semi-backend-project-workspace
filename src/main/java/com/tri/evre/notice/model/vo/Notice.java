package com.tri.evre.notice.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Notice {
	private Long noticeNo;
	private String noticeTitle;
	private String noticeContent;
	private String userId;
	private Date createDate;
	private Long views;
	private String publicYN;
}
