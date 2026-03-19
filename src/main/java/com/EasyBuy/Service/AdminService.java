package com.EasyBuy.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.EasyBuy.DTO.UserRequest;
import com.EasyBuy.Entity.User;
import com.EasyBuy.Repositories.AdminRepository;
import com.EasyBuy.Repositories.UserRepository;
import com.EasyBuy.Utility.Role;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

	private final AdminRepository adminRepo;
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

	public String createUser(UserRequest request) {
		log.info("Inside AdminService createUser .. ");

		if (userRepo.findByUserName(request.getUserName()).isPresent()) {
			throw new RuntimeException("User already exists.");
		}

		User user = new User();
		user.setUserName(request.getUserName());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.valueOf(request.getRole()));
		user.setTeamId(request.getTeamId());

		adminRepo.save(user);

		return "User Registered Successfully";

	}

	public String updateUser(UserRequest request) {
		log.info("Inside AdminService updateUser .. ");

		User user = userRepo.findById(request.getId()).orElseThrow(() -> new RuntimeException("User Not Found"));

		if (user == null) {
			throw new RuntimeException("User Not Found!");
		}

		if (StringUtils.isNotBlank(request.getUserName())) {
			user.setUserName(request.getUserName());
		}
		if (StringUtils.isNotBlank(request.getPassword())) {
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		if (StringUtils.isNotBlank(request.getRole())) {
			try {
				user.setRole(Role.valueOf(request.getRole()));
			} catch (IllegalArgumentException e) {
				throw new RuntimeException("Invalid role");
			}
		}

		if (request.getTeamId() != null) {
			user.setTeamId(request.getTeamId());
		}

		adminRepo.save(user);

		return "User Updated Successfully";

	}
}
