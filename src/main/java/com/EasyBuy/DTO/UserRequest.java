package com.EasyBuy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

	private Long id;
	private String userName;
	private String password;
	private Long teamId;
	private String role;
}
