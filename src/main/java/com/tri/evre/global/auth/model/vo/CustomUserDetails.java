package com.tri.evre.global.auth.model.vo;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomUserDetails implements UserDetails {
	private String username; //PK담아야됨
	private String password;
	private String name;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;
	private String status;
	
	

}
