package com.zero.template.services;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zero.template.dtos.UserDTO;
import com.zero.template.entities.User;
import com.zero.template.repositories.UserRepository;
import com.zero.template.utils.PasswordUtil;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private DozerBeanMapper beanMapper;

	public UserDTO saveUser(UserDTO user) throws Exception {
		User userEntity = beanMapper.map(user, User.class);
		byte[] salt = PasswordUtil.generateSalt();
		String encryptedPassword = PasswordUtil.getEncryptedPassword(user.getPassword(), salt);
		userEntity.setPassword(encryptedPassword);
		userEntity.setSalt(new String(salt));
		userEntity = repo.save(userEntity);
		user.setId(userEntity.getId());
		user.setPassword(null);
		return user;
	}
	
}
