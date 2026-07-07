package com.tri.evre.answer.model.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResponseAnswer {
	private String answerNo;
	private String answerCotent;
	private String userId;
}
