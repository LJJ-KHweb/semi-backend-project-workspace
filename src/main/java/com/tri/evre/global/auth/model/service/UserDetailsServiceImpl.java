package com.tri.evre.global.auth.model.service;
import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tri.evre.global.auth.model.dao.AuthMapper;
import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.user.model.dto.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final AuthMapper authMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//log.info("과연?? > {}", username);
		// 여기선 우리가 무엇을 해야하는가?
		
		UserDto user = authMapper.loadUser(username);
		 
		log.info("조회된 정보 : {}", user);
		
		if(user == null) {
			throw new UsernameNotFoundException("일치하는 회원이 없습니다.");
		}
		
		return CustomUserDetails.builder().username(user.getUserId())
											.password(user.getUserPwd())
											.name(user.getUserName())
											.authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())))
											.email(user.getEmail())
											.status(user.getStatus())
											//userRole이 필요함 admin할때 검사할때 일단 mapper에서 role도 조회해오니까 넣어줄게 06/30 재준 추가
											.role(user.getRole())
											.build();
	}

}
