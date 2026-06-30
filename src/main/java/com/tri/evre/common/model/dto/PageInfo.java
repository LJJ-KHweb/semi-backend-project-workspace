package com.tri.evre.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {
	private int page;
	private int size;
	private int boardCounts;
	private int offset;
	
	
	// 06/30 재준 추가
	// 앞단에서 page랑 사이즈를 받아서 처리해줄때 생성자로 바로 값을 넣고 싶어서 만듬
	
	public PageInfo(int page, int size) {
		super();
		this.page = page-1;
		this.size = size;
	
		this.offset = this.page * this.size;
	}
	
}
