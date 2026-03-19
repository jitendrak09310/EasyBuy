package com.EasyBuy.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EasyBuy.DTO.SignupRequest;
import com.EasyBuy.Service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(@RequestBody SignupRequest signupRequest) {
		log.info("Inside Signup method..");

		return ResponseEntity.ok(authService.signUp(signupRequest));
	}

}
