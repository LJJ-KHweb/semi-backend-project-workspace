package com.tri.evre.station.model.dto;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StationDto {
	private Long stationNo;
	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9가-힣]{2,16}$", message="충전소 이름의 형식이 잘못되었습니다.")
	private String stationName;
	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]{2,100}$", message="충전소 설명의 형식이 잘못되었습니다.")
	private String stationDesc;
	@NotBlank
	@Pattern(regexp="^[가-힣]{2,6}$", message="충전소 지역의 형식이 잘못되었습니다.")
	private String region;
	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9가-힣]{2,50}$", message="충전소 주소의 형식이 잘못되었습니다.")
	private String address;
	private int chargerCount;
	private double lat;
	private double lng;
	private Date createDate;
	private String status;
}
