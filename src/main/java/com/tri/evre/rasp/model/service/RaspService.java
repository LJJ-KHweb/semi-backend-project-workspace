package com.tri.evre.rasp.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.rasp.RaspNotFoundException;
import com.tri.evre.rasp.model.dao.RaspMapper;
import com.tri.evre.rasp.model.dto.RaspDayOfWeek;
import com.tri.evre.rasp.model.dto.RaspDto;
import com.tri.evre.rasp.model.dto.RaspResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RaspService {
	private final double ICE_CO2_EMISSION = 0.15; // 내연기간 자동차 탄소 배출 평균
	private final double ELECTRICITY_EMISSION_FACTOR = 0.46; // 전력을 만들때의 탄소 배출 평균
	private final RaspMapper raspMapper;

	
	// 탄소절감량 계산 공식
	private List<RaspResponseDto> calculateCarbonReduction(List<RaspDayOfWeek> results) {
		List<RaspResponseDto> responseResults = new ArrayList();
		for (RaspDayOfWeek r : results) {
			// 탄소 감축량 = 거리 * (내연기간 탄소배출량 - 전력생산 * 전비(전체거리 / 소비 전력량));
			double efficiency = r.getDistanceSum() / r.getKilowattSum();
			r.setCarbonReduction(r.getDistanceSum() * (ICE_CO2_EMISSION - (ELECTRICITY_EMISSION_FACTOR / efficiency)));
			responseResults.add(RaspResponseDto.builder().dayDate(r.getDayDate()+ "(" + r.getDay() +")")
														.distanceSum(r.getDistanceSum())
														.carbonReduction(r.getCarbonReduction())
														.build());
			
		}
		return responseResults;
	}
	
	public void save(RaspDto rasp) {
		int reuslt = raspMapper.save(rasp);
	}

	public List<RaspResponseDto> findAll() {

		List<RaspDayOfWeek> results = raspMapper.findAll();
		if (results.isEmpty()) {
			throw new RaspNotFoundException("라즈베리디비 조회 실패했습니다.");
		}
		List<RaspResponseDto> responseResults = calculateCarbonReduction(results);

		return responseResults;
	}

	public List<RaspResponseDto> findMyRaspAll(CustomUserDetails user) {
		List<RaspDayOfWeek> results = raspMapper.findMyRaspAll(user.getUsername());
		
		List<RaspResponseDto> responseResults = calculateCarbonReduction(results);
		return responseResults;
	}

}
