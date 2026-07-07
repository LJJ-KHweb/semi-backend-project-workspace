package com.tri.evre.answer.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class InsertAnswerDto {
	@NotBlank
	private String answerContent;
}
