package com.tri.evre.global.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tri.evre.global.configuration.fliter.JwtFilter;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.formLogin(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable)
				.cors(Customizer.withDefaults())
				.authorizeHttpRequests(requests -> {
					requests.requestMatchers(HttpMethod.POST,"/api/users", "/api/auth/login", "/api/auth/refresh").permitAll();
					requests.requestMatchers(HttpMethod.DELETE, "/api/auth/logout", "/api/boards/**").authenticated();
					requests.requestMatchers(HttpMethod.GET,"/api/boards/**","/uploads/**", "/api/chargeStations/**").permitAll();
					requests.requestMatchers(HttpMethod.PATCH,"/api/boards/**").authenticated();
					requests.requestMatchers(HttpMethod.POST, "/api/boards").authenticated();
					
					requests.requestMatchers(HttpMethod.GET,"/api/boards/**","/uploads/**").permitAll();
					requests.requestMatchers(HttpMethod.POST, "/api/boards").authenticated();
					//06/30---------------------- 상점 -----------------------------------
					requests.requestMatchers(HttpMethod.GET,"/api/shop").permitAll();
					requests.requestMatchers(HttpMethod.PATCH,"/api/shop/**").authenticated();
					
					//07/01
					requests.requestMatchers(HttpMethod.GET,"/api/shop/his-products").authenticated();
					requests.requestMatchers(HttpMethod.GET,"/api/admin/products/**").hasRole("ADMIN");
					
					// 07/03 심영도 관리자 충전소 전체조회, 충전소 추가
					requests.requestMatchers(HttpMethod.GET,"/api/admin/chargeStations/**").hasRole("ADMIN");
					requests.requestMatchers(HttpMethod.POST,"/api/admin/chargeStations/**").hasRole("ADMIN");
					
					// 07/06 심영도 관리자 충전소 수정, 삭제 | 충전기 전체조회, 추가
					requests.requestMatchers(HttpMethod.PATCH,"/api/admin/chargeStations/**").hasRole("ADMIN");
					requests.requestMatchers(HttpMethod.DELETE,"/api/admin/chargeStations/**").hasRole("ADMIN");
					requests.requestMatchers(HttpMethod.GET,"/api/admin/chargers/**").hasRole("ADMIN");
					requests.requestMatchers(HttpMethod.POST,"/api/admin/chargers/**").hasRole("ADMIN");
					
					// 7.7 심영도 관리자 충전기 수정, 삭제
					requests.requestMatchers(HttpMethod.PATCH,"/api/admin/chargers/**").hasRole("ADMIN");
					requests.requestMatchers(HttpMethod.DELETE,"/api/admin/chargers/**").hasRole("ADMIN");
					
					requests.requestMatchers(HttpMethod.PATCH, "/api/admin/products/{productNo}/restore").hasRole("ADMIN");
					
					// 07/02 선겸
					requests.requestMatchers(HttpMethod.POST,"/api/admin/products").hasRole("ADMIN");
					// 07/03 선겸
					requests.requestMatchers(HttpMethod.DELETE,"/api/admin/products/**").hasRole("ADMIN");
					requests.requestMatchers(HttpMethod.PATCH,"/api/admin/products/**").hasRole("ADMIN");
					
					// 07/06 선겸
					requests.requestMatchers(HttpMethod.POST,"/api/requires").authenticated();
					requests.requestMatchers(HttpMethod.GET,"/api/requires/**").authenticated();
					requests.requestMatchers(HttpMethod.GET,"/api/admin/requires/**").hasRole("ADMIN");
					
					
					// 07/07 선겸
					// 문의사항 응답하기
					requests.requestMatchers(HttpMethod.POST,"/api/admin/requires/**").hasRole("ADMIN");
					requests.requestMatchers(HttpMethod.GET,"/api/admin/adminPage").hasRole("ADMIN");
					
					
					// 06/30 재준 추가
					requests.requestMatchers(HttpMethod.GET,"/api/admin/boards/**").hasRole("ADMIN");
					requests.requestMatchers(HttpMethod.DELETE, "/api/admin/boards/**").hasRole("ADMIN");
					// 07/01 재준 추가 밑에 rasp관련
					requests.requestMatchers(HttpMethod.POST,"/api/rasp").permitAll();
					requests.requestMatchers(HttpMethod.GET,"/api/rasp").permitAll();
					// 07/02 재준 추가
					requests.requestMatchers(HttpMethod.GET,"/api/rasp/mypage").authenticated();
					requests.requestMatchers(HttpMethod.GET,"/api/admin/ranking", "api/admin/charts").hasRole("ADMIN");
					// 07/03
					requests.requestMatchers(HttpMethod.PATCH, "/api/users/mypage").authenticated();
					requests.requestMatchers(HttpMethod.GET,"/api/users/mypage").authenticated();
					// 공지사항
					requests.requestMatchers(HttpMethod.POST,"/api/notices").hasRole("ADMIN");
					requests.requestMatchers(HttpMethod.PATCH,"/api/notices/**").hasRole("ADMIN");
					requests.requestMatchers(HttpMethod.DELETE,"/api/notices/**").hasRole("ADMIN");
					
					
					requests.requestMatchers(HttpMethod.GET,"/api/notices/admin/**").hasRole("ADMIN");
					
					
					requests.requestMatchers(HttpMethod.GET, "/api/ranks/ranking").permitAll();
					
					
					requests.requestMatchers(HttpMethod.GET,"/api/notices").permitAll();
					requests.requestMatchers(HttpMethod.GET, "/api/notices/**").permitAll();
					
					
					requests.requestMatchers(HttpMethod.POST, "/api/users/drivingHistory").authenticated();
					requests.requestMatchers(HttpMethod.GET, "/api/admin/users/**").hasRole("ADMIN");
					requests.requestMatchers(HttpMethod.PATCH, "/api/admin/users").hasRole("ADMIN");
					
					
				}).sessionManagement(manager-> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
		configuration.setAllowedMethods(Arrays.asList("POST","PATCH","DELETE","GET","PUT","OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	
	
}

