package com.hackathon.customer.dto;

import lombok.Data;

@Data
public class UserInfoDTO {
	
	private String emailId;
	private String firstName;
	private String lastName;
	private String password;
	private boolean isStatus;
	private String role;
	private String accountNo;
    private double balance;
	

}
