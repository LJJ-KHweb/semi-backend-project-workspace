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
	
	public StationSearchRequest(PageInfo pageInfo, List<StationDto> stations) { // 관리자는 좌표랑 거리가 필요 없음
		super();
		this.pageInfo = pageInfo;
		this.stations = stations;
	}
}
