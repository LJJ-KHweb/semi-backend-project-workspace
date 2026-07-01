package com.tri.evre.shop.model.dto;

import java.util.List;

import com.tri.evre.common.model.dto.PageInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryPurchaseListDto {
	private PageInfo pageInfo;
	private List<HistoryPurchaseDto> historyPurchaseDto;
}
