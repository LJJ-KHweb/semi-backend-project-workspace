package com.tri.evre.charger.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tri.evre.charger.model.dto.ChargerDto;
import com.tri.evre.charger.model.dto.ChargerRequest;
import com.tri.evre.charger.model.dto.ChargerResponse;
import com.tri.evre.charger.model.vo.Charger;
import com.tri.evre.common.model.dto.PageInfo;

@Mapper
public interface ChargerMapper {

	int findAllChargerCount();

	List<ChargerDto> findAllCharger(PageInfo pageInfo);

	ChargerDto findByStationNo(Long stationNo);
	
	List<ChargerResponse> findChargerByStationNo(ChargerRequest charger);

	void insertCharger(Long stationNo);

	ChargerDto findByChargerNo(Long chargerNo);

	void updateCharger(Charger chargerEntity);

	void deleteCharger(Long chargerNo);

}
