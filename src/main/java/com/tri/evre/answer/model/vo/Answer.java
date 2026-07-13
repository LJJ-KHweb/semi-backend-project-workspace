package com.tri.evre.answer.model.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
	private Long requireNo;
	private String answerContent;
	private String userId;
}
