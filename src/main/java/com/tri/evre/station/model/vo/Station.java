package com.tri.evre.station.model.vo;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Station {
	private Long stationNo;
	@Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]{2,30}$", message = "충전소 이름의 형식이 잘못되었습니다.")
	private String stationName;
	@Pattern(regexp="^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s.,!?()/-]{2,200}$", message="충전소 설명의 형식이 잘못되었습니다.")
	private String stationDesc;
	@Pattern(regexp="^[가-힣\\s]{2,20}$", message="충전소 지역의 형식이 잘못되었습니다.")
	private String region;
	@Pattern(regexp="^[a-zA-Z0-9가-힣\\s.,()/-]{2,100}$", message="충전소 주소의 형식이 잘못되었습니다.")
	private String address;
	private int chargerCount;
	private double lat;
	private double lng;
}
