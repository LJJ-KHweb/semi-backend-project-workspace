package com.tri.evre.rasp.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tri.evre.rasp.model.dto.CarHistoryDto;
import com.tri.evre.rasp.model.dto.RaspDayOfWeek;
import com.tri.evre.rasp.model.dto.RaspDto;
import com.tri.evre.user.model.dto.DrivingHistory;

@Mapper
public interface RaspMapper {
	
	int save(RaspDto rasp);

	List<RaspDayOfWeek> findAll();

	List<RaspDayOfWeek> findMyRaspAll(String userId);

	CarHistoryDto findByDrivinHistory(DrivingHistory drivingHistory);

	void addDrivingHistory(CarHistoryDto carHistory);

}
