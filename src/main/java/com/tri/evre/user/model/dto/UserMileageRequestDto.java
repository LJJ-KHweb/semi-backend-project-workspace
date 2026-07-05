package com.tri.evre.user.model.dto;

import java.util.List;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.mileage.model.dto.MileageDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMileageRequestDto {
	private PageInfo pageInfo;
	private List<MileageDto> mileages;
}
