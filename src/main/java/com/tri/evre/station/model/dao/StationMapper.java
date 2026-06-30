package com.tri.evre.station.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tri.evre.station.model.dto.StationDto;
import com.tri.evre.station.model.dto.StationSearchRequest;

@Mapper
public interface StationMapper {
	
	@Select("""
				SELECT
					   STATION_NO,
					   STATION_NAME,
					   STATUS,
					   REGION,
					   ADDRESS,
					   LAT,
					   LNG
				  FROM (
						SELECT
							   STATION_NO,
							   STATION_NAME,
							   STATUS,
							   REGION,
							   ADDRESS,
							   LAT,
							   LNG,
							   (
            					6371 * ACOS(
                				COS(#{searchInfo.lat} * ACOS(-1) / 180)
                				* COS(LAT * ACOS(-1) / 180)
                				* COS((LNG - #{searchInfo.lng}) * ACOS(-1) / 180)
                				+ SIN(#{searchInfo.lat} * ACOS(-1) / 180)
                				* SIN(LAT * ACOS(-1) / 180)
            					)
								) AS DISTANCE_KM
						FROM STATION
						)
				 WHERE 
				 	   DISTANCE_KM <= #{searchInfo.distance}
				   AND
				   	   STATUS = 'Y'
				 ORDER 
				 	BY 
				 	   DISTANCE_KM
				OFFSET #{pageInfo.offset} ROWS FETCH NEXT #{pageInfo.size} ROWS ONLY
			""")
	List<StationDto> findAll(StationSearchRequest searchResponse);

	@Select("""
				SELECT
					   COUNT(*)
				  FROM (
						SELECT
							   STATUS,
							   LAT,
							   LNG,
							   (
            					6371 * ACOS(
                				COS(#{searchInfo.lat} * ACOS(-1) / 180)
                				* COS(LAT * ACOS(-1) / 180)
                				* COS((LNG - #{searchInfo.lng}) * ACOS(-1) / 180)
                				+ SIN(#{searchInfo.lat} * ACOS(-1) / 180)
                				* SIN(LAT * ACOS(-1) / 180)
            					)
								) AS DISTANCE_KM
						FROM STATION
						)
				 WHERE 
				 	   DISTANCE_KM <= #{searchInfo.distance}
				   AND
				   	   STATUS = 'Y'
				 ORDER 
				 	BY 
				 	   DISTANCE_KM   
			""")
	int findStationCount(StationSearchRequest searchRequest);

	@Select("""
				SELECT
					   COUNT(*)
				  FROM
					   STATION S
				  JOIN
					   CHARGER C ON S.STATION_NO = C.STATION_NO
				 WHERE
			 		   S.STATION_NO = #{stationNo}
			""")
	int findChargerCount(Long stationNo);

	@Select("""
				SELECT
					   STATION_NO
					 , STATION_NAME
					 , REGION
					 , ADDRESS
					 , STATION_DESC
					 , LAT
					 , LNG
					 , CREATE_DATE
				  FROM
				  	   STATION
				 WHERE
				 	   STATUS = 'Y'
				   AND
				   	   STATION_NO = #{stationNo}
			""")
	StationDto findByStationNo(Long stationNo);


}
