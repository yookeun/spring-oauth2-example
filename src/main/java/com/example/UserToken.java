package com.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserToken {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String accessToken;
	private String refresh_token;
	private String tokenType;
	private long expiresIn;
	private String scope;
}
