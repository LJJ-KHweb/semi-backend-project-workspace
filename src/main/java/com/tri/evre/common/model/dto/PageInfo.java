package com.tri.evre.common.model.dto;

import com.tri.evre.global.exception.page.InvalidPagingParameterException;

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
		validatePage(page);
		validateSize(size);
		this.page = page-1;
		this.size = size;
	
		this.offset = this.page * this.size;
	}
	
	private void validatePage(int page) {
		if(page < 1) {
			throw new InvalidPagingParameterException("page는 1이상이여야 합니다.");
		}
	}
	private void validateSize(int size) {
	    if (size < 1) {
	        throw new InvalidPagingParameterException("size는 1 이상이어야 합니다.");
	    }
	}
}