package com.tri.evre.charger.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Charger {
	private Long chargerNo;
	private String status;
	private Long stationNo;
	private String stationName;
	private String stationStatus;
}
