package com.tri.evre.user.model.vo;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class User {
	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9]${4,20}")
	private String userId;
	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9]${6,20}")
	private String userPwd;
	@NotBlank
	@Pattern(regexp= "^[0-9a-zA-Z._%+-]{4,20}+@[0-9a-zA-Z.-]{4,10}+\\.(com|kr)$")
	private String email;
	@NotBlank
	@Pattern(regexp="^[가-힣]{2,10}")
	private String userName;
	private String role;
	private Date createDate;
	private String status;
}
