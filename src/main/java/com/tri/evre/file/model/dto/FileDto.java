package com.tri.evre.file.model.dto;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileDto {
	private String filePath;
	private int fileOrder;
	private String originalName;
	private Long boardNo;
}
