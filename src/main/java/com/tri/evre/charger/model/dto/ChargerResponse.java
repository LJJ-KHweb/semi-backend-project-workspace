package com.tri.evre.charger.model.dto;

import java.util.List;

import com.tri.evre.common.model.dto.PageInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChargerResponse {
	private PageInfo pageInfo;
	private List<ChargerDto> chargers;
}
