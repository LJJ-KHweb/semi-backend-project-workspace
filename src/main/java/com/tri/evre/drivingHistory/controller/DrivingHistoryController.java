package com.tri.evre.drivingHistory.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/car")
@RequiredArgsConstructor
public class DrivingHistoryController {
	
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> saveDrivingHistory
	

}
