package com.zero.template.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zero.template.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUserName(String userName);
	
	public User findByUserNameOrEmailId(String userName, String emailId);
	
}
