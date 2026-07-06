package com.tri.evre.file.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class NoticeFile {
	private String filePath;
	private int fileOrder;
	private String originalName;
	private Long noticeNo;
}

