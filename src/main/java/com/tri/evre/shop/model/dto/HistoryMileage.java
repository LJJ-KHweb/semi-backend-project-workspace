package com.tri.evre.shop.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryMileage {
	private String userId;
	private Date createDate;
	private int change;
	private Long productNo;
}
