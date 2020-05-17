package com.zero.template.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zero.template.core.GenericResponse;
import com.zero.template.dtos.UserDTO;
import com.zero.template.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/whoami")
	public ResponseEntity<GenericResponse> whoAmI(HttpServletRequest request) {
		return null;
	}
	
	
}
