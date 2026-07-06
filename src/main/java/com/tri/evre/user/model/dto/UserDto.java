package com.tri.evre.user.model.dto;
import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class UserDto{
	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9]{4,20}$", message="아이디 형식이 올바르지 않습니다.")
	private String userId;
	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9]{4,20}$", message="비밀번호 형식이 올바르지 않습니다.")
	private String userPwd;
	@NotBlank
	@Pattern(regexp= "^[0-9a-zA-Z]{4,20}@[0-9a-zA-Z]{4,10}\\.(com|kr)$",  message = "이메일 형식이 올바르지 않습니다.")
	private String email;
	@NotBlank
	@Pattern(regexp="^[가-힣]{2,6}$", message="이름 형식이 올바르지 않습니다.")
	private String userName;
	private String role;
	private Date createDate;
	private String status;

}
