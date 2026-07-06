package com.tri.evre.file.model.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RequireFile {
	private String filePath;
	private int fileOrder;
	private String originalName;
	private Long requireNo;
}
