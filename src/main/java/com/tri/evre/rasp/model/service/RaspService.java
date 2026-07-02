package com.tri.evre.rasp.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
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
	private final double ICE_CO2_EMISSION = 0.15; // 내연기간 자동차 탄소 배출 평균
	private final double ELECTRICITY_EMISSION_FACTOR = 0.46; // 전력을 만들때의 탄소 배출 평균
	private final RaspMapper raspMapper;

	// 탄소절감량 계산 공식
	private void calculateCarbonReduction(List<RaspDayOfWeek> results) {
		for (RaspDayOfWeek r : results) {
			// 탄소 감축량 = 거리 * (내연기간 탄소배출량 - 전력생산 * 전비(전체거리 / 소비 전력량));
			double efficiency = r.getDistanceSum() / r.getKilowattSum();
			r.setCarbonReduction(r.getDistanceSum() * (ICE_CO2_EMISSION - (ELECTRICITY_EMISSION_FACTOR / efficiency)));
		}
	}

	public void save(RaspDto rasp) {
		int reuslt = raspMapper.save(rasp);
	}

	public List<RaspDayOfWeek> findAll() {

		List<RaspDayOfWeek> results = raspMapper.findAll();
		if (results == null) {
			throw new RaspNotFoundException("라즈베리디비 조회 실패했습니다.");
		}
		if (results.size() < 7) {
			throw new RaspNotFoundException("라즈베리디비 요일별 조회 실패했습니다.");
		}

		calculateCarbonReduction(results);

		return results;
	}

	public List<RaspDayOfWeek> findMyRaspAll(CustomUserDetails user) {
		List<RaspDayOfWeek> results = raspMapper.findMyRaspAll(user.getUsername());
		if (results == null) {
			throw new RaspNotFoundException("라즈베리디비 조회 실패했습니다.");
		}
		calculateCarbonReduction(results);
		return results;
	}

}
