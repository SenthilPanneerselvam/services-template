package com.zero.template.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zero.template.core.GenericResponse;
import com.zero.template.core.auth.UserProfile;
import com.zero.template.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/whoami")
	public ResponseEntity<GenericResponse> whoAmI() {
		UserProfile userProfile = service.getUserProfile(getUserId());
		return ResponseEntity.ok(new GenericResponse(userProfile));
	}
	
	
}
