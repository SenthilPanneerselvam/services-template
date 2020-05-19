package com.zero.template.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zero.template.core.GenericResponse;
import com.zero.template.core.Public;
import com.zero.template.dtos.LoginRequest;
import com.zero.template.dtos.UserDTO;
import com.zero.template.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;

	@Public
	@PostMapping("/login")
	public ResponseEntity<GenericResponse> login(@RequestBody LoginRequest request) throws Exception {
		String jws = userService.authenticate(request);
		return ResponseEntity.ok(new GenericResponse(jws));
	}
	
	@Public
	@PostMapping("/signup")
	public ResponseEntity<GenericResponse> signUp(@RequestBody UserDTO userdto) throws Exception {
		userdto = userService.saveUser(userdto);
		return ResponseEntity.ok(new GenericResponse(userdto));
	}
	
}
