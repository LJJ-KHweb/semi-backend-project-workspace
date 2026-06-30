package com.tri.evre.station.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.exception.station.StationNotFoundException;
import com.tri.evre.station.model.dao.StationMapper;
import com.tri.evre.station.model.dto.SearchInfo;
import com.tri.evre.station.model.dto.StationDto;
import com.tri.evre.station.model.dto.StationSearchRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StationService {
	
	private final StationMapper stationMapper;
	
	public StationSearchRequest findAll(int page, double lat, double lng, int dist) {
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(page);
		pageInfo.setSize(3);
		pageInfo.setOffset((page - 1) * pageInfo.getSize());
		
		StationSearchRequest searchResponse = new StationSearchRequest();
		searchResponse.setPageInfo(pageInfo);
		SearchInfo searchInfo = new SearchInfo();
		searchInfo.setLat(lat);
		searchInfo.setLng(lng);
		searchInfo.setDistance(dist);
		searchResponse.setSearchInfo(searchInfo);
		
		List<StationDto> stations = stationMapper.findAll(searchResponse);
		
		// log.info("stations : {}", stations);
		
		if(stations == null) {
			throw new StationNotFoundException("조회 결과가 없습니다.");
		}
		
		pageInfo.setBoardCounts(stationMapper.findStationCount(searchResponse));
		// log.info("boardCounts : {}", pageInfo.getBoardCounts());
		if (pageInfo.getBoardCounts() < 1) {
			throw new StationNotFoundException("조회 결과가 없습니다.");
		}
		
		searchResponse.setStations(stations);
		
		return searchResponse;
	}

}
