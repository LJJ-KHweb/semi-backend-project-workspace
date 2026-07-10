package com.tri.evre.station.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.api.model.vo.ApiResponse;
import com.tri.evre.global.api.model.vo.CustomHttpStatus;
import com.tri.evre.station.model.dto.StationDto;
import com.tri.evre.station.model.dto.StationSearchRequest;
import com.tri.evre.station.model.service.StationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chargeStations")
public class StationController {
	
	private final StationService stationService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<StationSearchRequest>> findAll(@RequestParam("size") int size,
																	 @RequestParam("page") int page,
																	 @RequestParam("lat") double lat,
																	 @RequestParam("lng") double lng,
																	 @RequestParam("dist") int dist) {
		StationSearchRequest searchResponse = stationService.findAll(new PageInfo(page, size), lat, lng, dist);
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("조회성공", searchResponse));
	}
	
	@GetMapping("/{stationNo}")
	public ResponseEntity<ApiResponse<StationDto>> findByStationNo(@PathVariable(name="stationNo") Long stationNo) {
		return ResponseEntity.status(CustomHttpStatus.SELECT_SUCCESS.getCode()).body(ApiResponse.success("조회성공", stationService.findByStationNo(stationNo)));
	}
}
