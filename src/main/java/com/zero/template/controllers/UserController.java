package com.zero.template.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zero.template.dtos.GenericResponse;
import com.zero.template.dtos.UserDTO;
import com.zero.template.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping()
	public ResponseEntity<GenericResponse> signUp(UserDTO userdto) throws Exception {
		userdto = service.saveUser(userdto);
		return ResponseEntity.ok(new GenericResponse(userdto));
	}

}
