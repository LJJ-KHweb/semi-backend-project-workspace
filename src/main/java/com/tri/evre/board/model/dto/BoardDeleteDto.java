package com.tri.evre.board.model.dto;

import jakarta.validation.constraints.Size;
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
public class BoardDeleteDto {
	@Size(max=3, min=2, message = "값이틀림" )
	private Long boardNo;
	private String userId;
	private String role;

}
