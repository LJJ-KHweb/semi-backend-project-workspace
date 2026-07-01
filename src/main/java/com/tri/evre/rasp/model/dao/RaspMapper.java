package com.tri.evre.rasp.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.tri.evre.rasp.model.dto.RaspDto;

@Mapper
public interface RaspMapper {
	
	int save(RaspDto rasp);

}
