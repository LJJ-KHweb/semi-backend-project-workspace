package com.tri.evre.token.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RefreshToken {
	private String userId;
	private String token;
	private Long expireDate;
	
}
