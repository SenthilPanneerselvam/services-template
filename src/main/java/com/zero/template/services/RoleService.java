package com.zero.template.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zero.template.core.BeanMapper;
import com.zero.template.dtos.RoleDTO;
import com.zero.template.entities.Role;
import com.zero.template.repositories.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository repo;
	
	public List<RoleDTO> getActiveRoles() {
		List<Role> roles = repo.findByActive(true);
		return BeanMapper.map(roles, RoleDTO.class);
	}

}
