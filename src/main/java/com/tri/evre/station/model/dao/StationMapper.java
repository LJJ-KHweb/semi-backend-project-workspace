package com.tri.evre.station.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.station.model.dto.SearchInfo;
import com.tri.evre.station.model.dto.StationDto;
import com.tri.evre.station.model.dto.StationSearchRequest;
import com.tri.evre.station.model.vo.Station;

@Mapper
public interface StationMapper {
	
	List<StationDto> findAll(StationSearchRequest searchResponse);

	int findStationCount(StationSearchRequest searchRequest);

	int findChargerCount(Long stationNo);

	StationDto findByStationNo(Long stationNo);

	int findAllStationCount();

	List<StationDto> findAllStation(PageInfo pageInfo);

	int findUnableCharger(Long stationNo);

	Long insertStation(Station stationEntity);

	int checkDuplicate(SearchInfo stationInfo);
	
	void updateStation(Station stationEntity);

	int checkDuplicateByNo(StationDto stationInfo);
	
	int deleteStation(Long stationNo);

	int findDeletedStation(Long stationNo);
	
}
