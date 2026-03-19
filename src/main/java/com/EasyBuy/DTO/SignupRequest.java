package com.EasyBuy.DTO;

import lombok.Data;

@Data
public class SignupRequest {

	private String userName;
	private String password;
	private Long teamId;

}
