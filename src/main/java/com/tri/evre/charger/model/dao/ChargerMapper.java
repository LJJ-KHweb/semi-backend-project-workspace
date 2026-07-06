package com.tri.evre.charger.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tri.evre.charger.model.dto.ChargerDto;
import com.tri.evre.common.model.dto.PageInfo;

@Mapper
public interface ChargerMapper {

	int findAllChargerCount();

	List<ChargerDto> findAllCharger(PageInfo pageInfo);

	ChargerDto findByChargerNo(Long stationNo);

	void insertCharger(Long stationNo);

}
