package com.zero.template.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zero.template.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	public Role findByCodeAndActive(String code,Boolean active);
	
	public List<Role> findByActive(Boolean active);

}
