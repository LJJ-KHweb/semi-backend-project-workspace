package com.tri.evre.file.model.dto;

import java.util.List;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.require.model.vo.Require;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequireListResponseAdmin {
	private PageInfo pageInfo;
	private List<Require> requires;
	
}
