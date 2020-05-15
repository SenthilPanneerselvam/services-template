package com.zero.template.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zero.template.core.GenericResponse;
import com.zero.template.services.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
	
	@Autowired
	private RoleService service;

	@GetMapping()
	public ResponseEntity<GenericResponse> getAllRoles() {
		GenericResponse response = new GenericResponse();
		response.setData(service.getActiveRoles());
		return ResponseEntity.ok(response);
	}
	
}
