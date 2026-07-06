package com.tri.evre.station.model.dto;

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
public class SearchInfo {
	private double lat;
	private double lng;
	private int distance;
	
	public SearchInfo(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}
}
