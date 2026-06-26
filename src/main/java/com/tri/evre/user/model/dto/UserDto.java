package com.tri.evre.user.model.dto;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
	private String userId;
	private String userPwd;
	private String email;
	private String userName;
	private String role;
	private Date createDate;
	private String status;

}
