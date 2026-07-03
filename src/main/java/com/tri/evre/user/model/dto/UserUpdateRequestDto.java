package com.tri.evre.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {
	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9]{4,20}$", message="비밀번호 형식이 올바르지 않습니다.")
	private String rawPwd;
	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9]{4,20}$", message="비밀번호 형식이 올바르지 않습니다.")
	private String userPwd;
	@NotBlank
	@Pattern(regexp= "^[0-9a-zA-Z]{4,20}@[0-9a-zA-Z]{4,10}\\.(com|kr)$",  message = "이메일 형식이 올바르지 않습니다.")
	private String email;

}
