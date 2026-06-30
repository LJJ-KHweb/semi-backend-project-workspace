package com.tri.evre.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Board {
	private Long boardNo;
	private String boardTitle;
	private String boardContent;
	private String userId;
	private Date createDate;
	private Long views;
}
