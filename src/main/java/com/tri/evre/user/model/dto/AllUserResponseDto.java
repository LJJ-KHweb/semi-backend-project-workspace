package com.tri.evre.user.model.dto;

import java.util.List;

import com.tri.evre.common.model.dto.PageInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllUserResponseDto {
	private PageInfo pageInfo;
	private List<UserMaskedDto> users;
}
