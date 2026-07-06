package com.tri.evre.require.model.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Require {
	private Long requireNo;
	private String requireTitle;
	private String requireContent;
	private String userId;
}
