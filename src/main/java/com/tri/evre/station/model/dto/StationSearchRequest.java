package com.tri.evre.station.model.dto;

import java.util.List;

import com.tri.evre.common.model.dto.PageInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StationSearchRequest {
	private PageInfo pageInfo;
	private SearchInfo searchInfo;
	private List<StationDto> stations;
}
