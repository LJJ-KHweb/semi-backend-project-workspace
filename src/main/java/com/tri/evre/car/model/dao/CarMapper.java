package com.tri.evre.car.model.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tri.evre.user.model.dto.DrivingHistory;

@Mapper
public interface CarMapper {

	@Insert("""
				INSERT
				  INTO
				  		CAR
				VALUES
						(
						#{drivingHistory.carNo}
					 ,	#{drivingHistory.startTime}
					 ,	#{drivingHistory.finishTime}
					 ,	#{userId}
						)
			""")
	int save(@Param(value ="drivingHistory") DrivingHistory drivingHistory,@Param(value = "userId") String userId);

	@Select("""
				SELECT 
						COUNT(*)
				  FROM 
				  		CAR
			 	 WHERE 
			 	 		CAR_NO = #{carNo}
			   	   AND 
			   	   		START_TIME <= #{finishTime}
			   	   AND 
			   	   		FINISH_TIME >= #{startTime}
			""")
	int validateCarUsagePeriod(DrivingHistory drivingHistory);  

}
