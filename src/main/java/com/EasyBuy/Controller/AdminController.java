package com.EasyBuy.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EasyBuy.DTO.UserRequest;
import com.EasyBuy.Service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/create-user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> createUser(@RequestBody UserRequest request) {
		log.info("Inside createUser ...");
		return ResponseEntity.ok(adminService.createUser(request));
	}
	
	@PostMapping("/update-user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> updateUser(@RequestBody UserRequest request) {
		log.info("Inside updateUser ...");
		return ResponseEntity.ok(adminService.updateUser(request));
	}
}
