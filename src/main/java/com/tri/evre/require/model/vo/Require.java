package com.tri.evre.require.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Require {
	private Long requireNo;
	private String requireTitle;
	private Date createDate;
	private String userId;
}
