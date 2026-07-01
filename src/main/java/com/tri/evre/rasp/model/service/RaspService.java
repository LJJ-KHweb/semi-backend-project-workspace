package com.tri.evre.rasp.model.service;

import org.springframework.stereotype.Service;

import com.tri.evre.rasp.model.dao.RaspMapper;
import com.tri.evre.rasp.model.dto.RaspDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RaspService {
	private final RaspMapper raspMapper;
	
	public void save(RaspDto rasp) {
		int reuslt = raspMapper.save(rasp);
	}

}
