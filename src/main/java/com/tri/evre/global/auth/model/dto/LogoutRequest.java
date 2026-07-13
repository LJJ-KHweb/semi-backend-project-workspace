package com.tri.evre.global.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest {
	@NotBlank(message = "리프레시 토큰은 필수입니다.")
	private String refreshToken;
}
