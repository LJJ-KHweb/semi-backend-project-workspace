package com.tri.evre.shop.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryPurchaseDto {
	private String productName; 
	private int change;
	private Date createDate;
}
