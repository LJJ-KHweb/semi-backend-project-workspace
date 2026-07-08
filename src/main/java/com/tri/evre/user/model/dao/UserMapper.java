package com.tri.evre.user.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.mileage.model.dto.MileageHistoryResponseDto;
import com.tri.evre.user.model.dto.UserDto;
import com.tri.evre.user.model.dto.UserRoleRequestDto;
import com.tri.evre.user.model.vo.User;

@Mapper
public interface UserMapper {

	int countByUserId(String userId);

	void signup(User userEntity);

	String findPwd();

	int update(User userEntity);

	List<MileageHistoryResponseDto> findAllMileageHistory(@Param(value = "pageInfo") PageInfo pageInfo,@Param(value = "username") String username);

	List<UserDto> findAllUser(PageInfo pageInfo);

	void updateUserRole(UserRoleRequestDto user);

	int findAllMileageHistoryCounts(String userId);

	Integer findMileageSum(String userId);

	int sumUsers();

	int addMileage(@Param(value = "mileage") int mileage, @Param(value = "userId") String userId);

}
