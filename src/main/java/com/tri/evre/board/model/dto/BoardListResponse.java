package com.tri.evre.board.model.dto;

import java.util.List;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.notice.model.dto.NoticeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardListResponse {
	private PageInfo pageInfo;
	private List<NoticeDto> notices;
	private List<BoardDto> boards;
	public BoardListResponse(PageInfo pageInfo, List<BoardDto> boards) {
		super();
		this.pageInfo = pageInfo;
		this.boards = boards;
	}
	
}
