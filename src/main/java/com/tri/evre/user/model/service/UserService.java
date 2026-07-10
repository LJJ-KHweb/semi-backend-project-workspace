package com.tri.evre.user.model.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tri.evre.car.model.dao.CarMapper;
import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.car.InvalidVehicleUsageException;
import com.tri.evre.global.exception.shop.MileageHistoryCreateException;
import com.tri.evre.global.exception.user.ConcurrentUpdateException;
import com.tri.evre.global.exception.user.DuplicateResourceException;
import com.tri.evre.global.exception.user.PasswordMismatchException;
import com.tri.evre.global.exception.user.UserNotFoundException;
import com.tri.evre.mileage.model.dto.MileageHistoryResponseDto;
import com.tri.evre.rasp.model.dao.RaspMapper;
import com.tri.evre.rasp.model.dto.CarHistoryDto;
import com.tri.evre.user.model.dao.UserMapper;
import com.tri.evre.user.model.dto.DrivingHistory;
import com.tri.evre.user.model.dto.UserDto;
import com.tri.evre.user.model.dto.UserMileageRequestDto;
import com.tri.evre.user.model.dto.UserUpdateRequestDto;
import com.tri.evre.user.model.vo.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final RaspMapper raspMapper;
	private final PasswordEncoder passwordEncoder;
	private final CarMapper carMapper;

	public void signup(UserDto user) {
		//아이디가 중복인지 확인
		validateDuplicateUserId(user.getUserId());

		User userEntity = User.builder().userId(user.getUserId())
										.userPwd(passwordEncoder.encode(user.getUserPwd()))
										.userName(user.getUserName())
										.email(user.getEmail())
										.build();
		userMapper.signup(userEntity);

	}

	public void update(@Valid UserUpdateRequestDto updateUser, CustomUserDetails user) {

		// 아이디 중복체크(회원인지 확인)
		ensureUserIdNotExists(user.getUsername());
		
		// 회원까지는 맞음 비밀번호가 일치하는지 
		checkPwd(updateUser.getRawPwd(), user.getPassword());
		
		//비밀번호까지 일치함 그럼 db에서 회원 정보 수정하기 (이메일과 비번)
		User userEntity = User.builder().userId(user.getUsername())
									.userPwd(passwordEncoder.encode(updateUser.getUserPwd()))
									.email(updateUser.getEmail())
									.build();
		
		
		// 처리를 해줘야 하는 이유는 만약 동시 요청이 들어올 경우가있음
		// 회원 탈퇴를 하는 동시에 변경요청이 들어온다면 UpdateRow가 0이 찍힘
		int result = userMapper.update(userEntity);
		if(result < 1) {
			throw new ConcurrentUpdateException("회원 정보 수정에 실패했습니다");
		}
	}

	// 회원인지 확인하는 방법
	private void ensureUserIdNotExists(String userId) {
		if(userMapper.countByUserId(userId) == 0) {
			throw new UserNotFoundException("일치하는 회원이 없습니다.");
		}
	}
	
	// 아이디 중복은 여러군대에서 쓸거 같아서 책임 분리 해놈
	private void validateDuplicateUserId(String userId) {
		if (userMapper.countByUserId(userId) > 0) {
			// 예외 처리 아이디가 중복됨
			throw new DuplicateResourceException("이미 사용중인 아이디입니다");
		}
	}
	// 비밀번호 검증 확인 나중에 삭제 같은거 할때 또 필요할거 같아서 분리 해놈
	private void checkPwd(String rawPwd, String encodePassword) {
		if(!passwordEncoder.matches(rawPwd, encodePassword)) {
			throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
		}
	}

	
	// 마이페이지 마일리지 내역 조회
	
	public UserMileageRequestDto findAllMileageHistory(PageInfo pageInfo, CustomUserDetails user) {
		List<MileageHistoryResponseDto> mileages =userMapper.findAllMileageHistory(pageInfo, user.getUsername());
		pageInfo.setBoardCounts(userMapper.findAllMileageHistoryCounts(user.getUsername()));
		Integer mileageSum = userMapper.findMileageSum(user.getUsername());
		if(mileageSum == null) {
			mileageSum = 0;
		}
		
		return UserMileageRequestDto.builder().mileages(mileages)
												.pageInfo(pageInfo)
												.mileageSum(mileageSum)
												.build();
	}

	
	@Transactional
	public void saveDrivingHistory(CustomUserDetails user, DrivingHistory drivingHistory) {
		//차량 번호 검증
		validateCarNumber(drivingHistory.getCarNo());
		
		// Car테이블에 저장해줘야됨
		int result = carMapper.save(drivingHistory, user.getUsername());
		if(result < 1) {
			throw new InvalidVehicleUsageException("공유전기차 이용내역 저장에 실패했습니다.");
		}
		addMileage(drivingHistory, user.getUsername());
		// 마일리지 히스토리 추가 메소드 호출하기
	}
	
	
	// 회원 마일리지 적립 메소드
	private void addMileage(DrivingHistory drivingHistory, String userId) {
		
		// 라즈베리파이 데이터에서 차량번호와 운행 시작/종료 시간 사이의 누적 주행거리(kmSum) 조회
		CarHistoryDto carHistory = raspMapper.findByDrivinHistory(drivingHistory);
		if (carHistory.getDistanceSum() == null) {
			return;
		}
		carHistory.setUserId(userId);
		raspMapper.addDrivingHistory(carHistory);
		int result = userMapper.addMileage(carHistory.getDistanceSum() * 2, userId);
		if(result < 1) {
			throw new MileageHistoryCreateException("마일리지 추가 실패했습니다.");
		}
	}
	
	
	// 차량 번호 유효성 검증 메소드
	private void validateCarNumber(String carNo) {
		Set<String> carSet = Set.of(
			    "52가 3108",
			    "32가 7257",
			    "47가 5706",
			    "26가 5771",
			    "23가 1266",
			    "29가 7257",
			    "31가 2012",
			    "71가 0715",
			    "77가 7777",
			    "17가 2311"
			);
		if(!carSet.contains(carNo)) {
			throw new InvalidVehicleUsageException("유효하지 않은 차량 번호 입니다.");
		}
	}
	

}
