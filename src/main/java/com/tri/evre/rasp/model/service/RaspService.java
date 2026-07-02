package com.tri.evre.rasp.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tri.evre.global.exception.rasp.RaspNotFoundException;
import com.tri.evre.rasp.model.dao.RaspMapper;
import com.tri.evre.rasp.model.dto.RaspDayOfWeek;
import com.tri.evre.rasp.model.dto.RaspDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RaspService {
	private final double ICE_CO2_EMISSION = 0.15;   // kgCO₂/km
	private final double ELECTRICITY_EMISSION_FACTOR = 0.46; // kgCO₂/kWh
	private final RaspMapper raspMapper;
	
	public void save(RaspDto rasp) {
		int reuslt = raspMapper.save(rasp);
	}

	public List<RaspDayOfWeek> findAll() {
		
		List<RaspDayOfWeek> results = raspMapper.findAll();
		if(results == null) {
			throw new RaspNotFoundException("라즈베리디비 조회 실패했습니다.");
		}
		if(results.size() < 7) {
			throw new RaspNotFoundException("라즈베리디비 요일별 조회 실패했습니다.");
		}
		
		for(RaspDayOfWeek r : results) {
			test(r);
		}
		return results;
	}
	
	private void test(RaspDayOfWeek r) {
		double efficiency = r.getDistanceSum() / r.getKilowattSum();
		r.setCarbonReduction (r.getDistanceSum() * (ICE_CO2_EMISSION - (ELECTRICITY_EMISSION_FACTOR / efficiency)));
	}

}
