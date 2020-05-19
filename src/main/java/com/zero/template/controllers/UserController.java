package com.zero.template.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zero.template.core.GenericResponse;
import com.zero.template.core.auth.RequestContext;
import com.zero.template.core.auth.UserProfile;
import com.zero.template.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private RequestContext context;
	
	@GetMapping("/whoami")
	// TODO: Create a base controller to retrieve the user id, user profile or other common functions
	public ResponseEntity<GenericResponse> whoAmI() {
		UserProfile userProfile = service.getUserProfile(context.getUserProfile().getUserId());
		return ResponseEntity.ok(new GenericResponse(userProfile));
	}
	
	
}
