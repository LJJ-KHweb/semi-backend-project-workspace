package com.tri.evre.rank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankDto {
	private Long ranking;
	private String userId;
	private String userName;
	private Double carbonSum;
	private Double distanceSum;
	
}
