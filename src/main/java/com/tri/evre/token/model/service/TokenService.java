package com.tri.evre.token.model.service;

import java.sql.Date;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tri.evre.global.auth.model.vo.CustomUserDetails;
import com.tri.evre.global.exception.CustomAuthenticationException;
import com.tri.evre.token.model.dao.TokenMapper;
import com.tri.evre.token.model.vo.RefreshToken;
import com.tri.evre.token.util.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
	private final JwtUtil tokenUtil;
	private final TokenMapper tokenMapper;

	public Map<String, String> getTokens(CustomUserDetails user) {
		/*
		 * String accessToken = tokenUtil.getAccessToken(user); log.info("이게토큰 : {} ",
		 * accessToken); String refreshToken = tokenUtil.getRefreshToken(user);
		 */
		// 리프레시 토큰은 DB에 저장
		Map<String, String> tokens = createTokens(user);

		saveToken(tokens.get("refreshToken"), user.getUsername());

		return tokens;
	}

	// 토큰을 만들어서 반환해주는 메소드
	private Map<String, String> createTokens(CustomUserDetails user) {
		return Map.of("accessToken", tokenUtil.getAccessToken(user), "refreshToken", tokenUtil.getRefreshToken(user));
	}

	// 리프레시토큰을 받아서 DB에 INSERT해주는 메소드
	private void saveToken(String token, String userId) {
		RefreshToken refreshToken = RefreshToken.builder().userId(userId).token(token)
				.expireDate(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 3)).build();

		tokenMapper.saveToken(refreshToken);
	}

	// 로그아웃 요청있을때 DB에있는거 싹 지워주는 메소드
	@Transactional
	public void logout(String refreshToken, String userId) {

		int result = tokenMapper.deleteToken(userId, refreshToken);
		if (result < 1) {
			log.info("잘못됨");
		} else {
			log.info("delete성공");
		}

	}
	// 추후 AccessToken이 만료기간이 지나서 토큰 갱신 요청이 들어왔을때
	// 사용자에게 전달받은 RefreshToken이 DB에 존재하면서 만료기간이 지나지 않았는지 검증하는 메소드

	public Map<String, String> tokenRotation(String refreshToken) {
		RefreshToken token = tokenMapper.findByToken(refreshToken);
		if (token == null) {
			throw new CustomAuthenticationException("일치하는 트큰이 없습니다");
		}
		if (token.getExpireDate() < System.currentTimeMillis()) {
			// 이게 리프레시 토큰도 만료되었을 경우
			tokenMapper.deleteToken(token.getUserId(), refreshToken);
			throw new CustomAuthenticationException("리프레시 트큰만료");
		}

		// refreshToken 만료되진 않음

		Claims claims = tokenUtil.parseJwt(token.getToken());
		String userId = claims.getSubject();

		String userName = (String) claims.get("userName");
		CustomUserDetails user = CustomUserDetails.builder().name(userName).username(userId).build();

		Map<String, String> tokens = createTokens(user);

		saveToken(tokens.get("refreshToken"), userId);

		return tokens;
	}

}
