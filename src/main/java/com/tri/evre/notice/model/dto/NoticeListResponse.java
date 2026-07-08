package com.tri.evre.notice.model.dto;

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
public class NoticeListResponse {
	private PageInfo pageInfo;
	private List<NoticeDto> notices;
	
	
}
