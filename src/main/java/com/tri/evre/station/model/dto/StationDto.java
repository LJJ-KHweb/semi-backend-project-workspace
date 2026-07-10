package com.tri.evre.station.model.dto;

import java.sql.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
	@Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]{2,30}$", message = "충전소 이름의 형식이 잘못되었습니다.")
	private String stationName;
	@Pattern(regexp="^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s.,!?()/-]{2,200}$", message="충전소 설명의 형식이 잘못되었습니다.")
	private String stationDesc;
	@Pattern(regexp="^[가-힣\\s]{2,20}$", message="충전소 지역의 형식이 잘못되었습니다.")
	private String region;
	@Pattern(regexp="^[a-zA-Z0-9가-힣\\s.,()/-]{2,100}$", message="충전소 주소의 형식이 잘못되었습니다.")
	private String address;
	@Min(value = 0, message = "충전기 개수가 음수일 수 없습니다.")
	@Max(value = 99, message = "충전기 개수는 99개 까지만 등록 가능합니다.")
	private int chargerCount;
	private double lat;
	private double lng;
	private Date createDate;
	private String status;
	private int unableChargerCount; // 충전소에 충전기 몇대 고장났는 지
	
	public StationDto(Long stationNo, double lat, double lng) {
		super();
		this.stationNo = stationNo;
		this.lat = lat;
		this.lng = lng;
	}
}
