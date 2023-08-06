package com.hackathon.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTAuthResponse {

	private String accessToken;
	private String tokenType;
}
