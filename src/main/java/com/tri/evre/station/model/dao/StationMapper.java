package com.tri.evre.station.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.station.model.dto.SearchInfo;
import com.tri.evre.station.model.dto.StationDto;
import com.tri.evre.station.model.dto.StationSearchRequest;
import com.tri.evre.station.model.vo.Station;

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

	@Select("""
				SELECT
					   COUNT(*)
				  FROM
				  	   STATION
			""")
	int findAllStationCount();

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
					 , STATUS
				  FROM
				  	   STATION
				 ORDER
				 	BY
				 	   STATION_NO DESC
				OFFSET #{offset} ROWS FETCH NEXT #{size} ROWS ONLY
			""")
	List<StationDto> findAllStation(PageInfo pageInfo);

	@Select("""
				SELECT
					   COUNT(*)
				  FROM
					   STATION S
				  JOIN
					   CHARGER C ON S.STATION_NO = C.STATION_NO
				 WHERE
			 		   S.STATION_NO = #{stationNo}
			 	   AND
			 	   	   C.STATUS = 'N'
			""")
	int findUnableCharger(Long stationNo);

	@Insert("""
				INSERT
				  INTO
				  	   STATION
				  	   (
				  	   STATION_NO
				  	 , STATION_NAME
				  	 , REGION
			 		 , ADDRESS
			 		 , STATION_DESC
			  		 , LAT
			  		 , LNG
				  	   )
				VALUES
					   (
					   	SEQ_STATION.NEXTVAL
					  , #{stationName}
					  , #{region}
					  , #{address}
					  , #{stationDesc}
					  , #{lat}
					  , #{lng}
					   )
			""")
	void insertStation(Station stationEntity);

	@Select("""
				SELECT
					   COUNT(*)
				  FROM
				       STATION
				 WHERE
				 	   LAT = #{lat}
				   AND
				   	   LNG = #{lng}
			""")
	int checkDuplicate(SearchInfo stationInfo);

	@Update("""
				UPDATE
					   STATION
				   SET
				   	   STATION_NAME = #{stationName}
				   	 , STATION_DESC = #{stationDesc}
				   	 , REGION = #{region}
			 		 , ADDRESS = #{address}
			 		 , LAT = #{lat}
			  		 , LNG = #{lng}
			  		 , STATUS = #{status}
			  	 WHERE
			  	 	   STATION_NO = #{stationNo}
			""")
	void updateStation(Station stationEntity);


	
	
}
