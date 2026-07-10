package com.tri.evre.rank.model.dto;

import java.util.List;

import com.tri.evre.common.model.dto.PageInfo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RankResponseDto {
	private PageInfo pageInfo;
	private List<RankDto> ranks;
	private RankDto myRank;

}
