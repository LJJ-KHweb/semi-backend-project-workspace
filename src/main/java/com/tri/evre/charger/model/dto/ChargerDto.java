package com.tri.evre.charger.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChargerDto {
	private Long chargerNo;
	private String status;
	private Long stationNo;
	private String stationName;
	private String stationStatus;
}
