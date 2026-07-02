package com.tri.evre.rasp.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tri.evre.rasp.model.dto.RaspDayOfWeek;
import com.tri.evre.rasp.model.dto.RaspDto;

@Mapper
public interface RaspMapper {
	
	int save(RaspDto rasp);

	List<RaspDayOfWeek> findAll();

}
