package com.tri.evre.require.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequireResponse {
	private Long requireNo;
	private String requireTitle;
	private Date createDate;
}
