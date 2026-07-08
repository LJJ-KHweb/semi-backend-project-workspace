package com.tri.evre.car.model.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

}
