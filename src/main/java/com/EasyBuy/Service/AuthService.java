package com.EasyBuy.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.EasyBuy.DTO.LoginRequest;
import com.EasyBuy.DTO.SignupRequest;
import com.EasyBuy.Entity.User;
import com.EasyBuy.Repositories.UserRepository;
import com.EasyBuy.Utility.JwtUtil;
import com.EasyBuy.Utility.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public String signUp(SignupRequest signupRequest) {
		log.info("Inside AuthService signUp .. ");

		if (userRepo.findByUserName(signupRequest.getUserName()).isPresent()) {
			throw new RuntimeException("User already exists.");
		}

		User user = new User();
		user.setUserName(signupRequest.getUserName());
		user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		user.setRole(Role.USER);
		user.setTeamId(signupRequest.getTeamId());

		userRepo.save(user);

		return "User Registered Successfully";
	}

	public String login(LoginRequest request) {
		log.info("Inside AuthService login .. ");

		User user = userRepo.findByUserName(request.getUserName())
				.orElseThrow(() -> new RuntimeException("User Not Found"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid Password!");
		}
		return jwtUtil.generateToken(user);

	}

}
