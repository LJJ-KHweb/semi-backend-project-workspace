package com.tri.evre.user.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserMaskedDto {
	private String userId;
	private String userName;
	private String email;
	private String role;
	private Date createDate;
	private String originalUserId;
	
	public UserMaskedDto(String userId, String userName, String email, String role, Date createDate, String originalUserId){
	    this.userId = maskId(userId);
	    this.userName =maskName(userName);
	    this.email = maskEmail(email);
	    this.role = role;
	    this.createDate = createDate;
	    this.originalUserId = originalUserId;

	}
	
	// 이름 
	private String maskName(String name) {

		if (name == null || name.length() < 2)
			return name;

		return name.charAt(0) + "*" + name.substring(name.length() - 1);
	}
	
	private String maskId(String id) {

	    if (id == null || id.length() < 4)
	        return id;

	    return id.substring(0, 2)
	            + "*".repeat(id.length() - 2);
	}
	
	private String maskEmail(String email) {

	    int idx = email.indexOf("@");

	    if (idx <= 2)
	        return email;

	    String front = email.substring(0, idx);

	    return front.substring(0, 2)
	            + "*".repeat(front.length() - 3)
	            + front.charAt(front.length() - 1)
	            + email.substring(idx);
	}
}
