package com.tri.evre.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleRequestDto {
	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9]{4,20}$", message="아이디 형식이 올바르지 않습니다.")
	private String userId;
	@Pattern(regexp = "^ROLE_(USER|ADMIN|BAN)$", message = "권한은 ROLE_USER, ROLE_ADMIN, ROLE_BAN만 가능합니다.")
	private String role;
}
