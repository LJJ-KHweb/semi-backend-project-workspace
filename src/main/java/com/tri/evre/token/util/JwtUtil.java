package com.tri.evre.token.util;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tri.evre.global.auth.model.vo.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {
	// 토큰을 만드는 기능 , 토큰을 검증하는 기능
	
	// header, payload, signature
	//D9uLKVc4FE0IEheN3pLLEb8GKhHRsG8cdOxazHnEkRm3CGAeB+xVbt8Dbob1QhRB
	
	@Value("${jwt.secret}")
	private String secretKey;
	private SecretKey key;
	
	@PostConstruct
	public void init() {
		//log.info("{}", secretKey);
		byte[] arr =Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(arr);
		
	}

	public String getAccessToken(CustomUserDetails user) {
		return Jwts.builder()
				.subject(user.getUsername())
				.issuedAt(new Date())
		//	   .expiration(new Date(System.currentTimeMillis()+(1000*60*15)));
		//	   .expiration(new Date(System.currentTimeMillis()+ TimeUnit.MINUTES.toMinutes(15))));
		       .expiration(Date.from(Instant.now().plus(Duration.ofMinutes(60*5))))
		       .claim("userName", user.getName())
		       .signWith(key)
		       .compact();
	}
	/*
	 * 	AccessToken은 일반적으로 짧은 만료기간을 가지도록 생성함
	 * 	+
	 * 	RefreshToken은 토큰은 일반적으로 AccessToken에 비해 긴 만료시간으로 설정해서 생성함
	 *	  
	 */

	public String getRefreshToken(CustomUserDetails user) {
		return  Jwts.builder()
				.subject(user.getUsername())
				.issuedAt(new Date())
				.expiration(Date.from(Instant.now().plus(Duration.ofDays(1))))
				.claim("userName", user.getName())
				.signWith(key)
				.compact();
	}
	
	
	public Claims parseJwt(String token) {
		return Jwts.parser()
					.verifyWith(key)
					.build()
					.parseSignedClaims(token)
					.getPayload();
	}
	
	// 세미 프로젝트 조 
	
}
