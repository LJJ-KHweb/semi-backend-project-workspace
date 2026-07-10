package com.tri.evre.station.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.exception.charger.ChargerReadException;
import com.tri.evre.global.exception.station.StationNotFoundException;
import com.tri.evre.global.exception.station.StationReadException;
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
	
	public StationSearchRequest findAll(PageInfo pageInfo, double lat, double lng, int dist) {
		pageInfo.setBoardCounts(stationMapper.findAllStationCount());
		
		StationSearchRequest searchResponse = new StationSearchRequest();
		searchResponse.setPageInfo(pageInfo);
		SearchInfo searchInfo = new SearchInfo(lat,lng,dist);
		searchResponse.setSearchInfo(searchInfo);
		
		List<StationDto> stations = stationMapper.findAll(searchResponse);
		if(stations == null) {
			throw new StationNotFoundException("일치하는 충전소가 없습니다.");
		}
		
		pageInfo.setBoardCounts(stationMapper.findStationCount(searchResponse));
		if (pageInfo.getBoardCounts() < 1) {
			throw new StationNotFoundException("일치하는 충전소가 없습니다.");
		}
		
		for(StationDto station : stations) {
			int chargerCount = stationMapper.findChargerCount(station.getStationNo());
			station.setChargerCount(chargerCount);
			int unableChargers = stationMapper.findUnableCharger(station.getStationNo());
			station.setUnableChargerCount(unableChargers);
		}
		searchResponse.setStations(stations);
		
		return searchResponse;
	}

	public StationDto findByStationNo(Long stationNo) {
		
		StationDto station = stationMapper.findByStationNo(stationNo);
		if(station == null) {
			// 충전소 데이터 조회 과정에서 조회 또는 서버 내부 오류가 발생한 경우
			throw new StationReadException("충전소 조회에 실패했습니다.");
		}
		
		int chargerCount = stationMapper.findChargerCount(station.getStationNo()); 
		if(chargerCount < 0) {
			// 충전기 데이터 조회 과정에서 조회 또는 서버 내부 오류가 발생한 경우
			throw new ChargerReadException("충전기 조회에 실패했습니다.");
		}
		
		int unableChargers = stationMapper.findUnableCharger(station.getStationNo());
		station.setUnableChargerCount(unableChargers);
		station.setChargerCount(chargerCount);
		
		return station;
	}

}
