package com.zero.template.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zero.template.core.BadRequestException;
import com.zero.template.core.BeanMapper;
import com.zero.template.core.UnAuthenticatedException;
import com.zero.template.dtos.LoginRequest;
import com.zero.template.dtos.UserDTO;
import com.zero.template.entities.Role;
import com.zero.template.entities.User;
import com.zero.template.repositories.RoleRepository;
import com.zero.template.repositories.UserRepository;
import com.zero.template.utils.PasswordUtil;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	public UserDTO saveUser(UserDTO user) throws Exception {
		// validate the request
		User existing = userRepo.findByUserNameOrEmailId(user.getUserName(), user.getEmailId());
		if(existing != null)
			throw new BadRequestException("Account already exists");
		User userEntity = BeanMapper.map(user, User.class);
		// encrypting the password
		String salt = PasswordUtil.generateSalt();
		String encryptedPassword = PasswordUtil.getEncryptedPassword(user.getPassword(), salt);
		userEntity.setPassword(encryptedPassword);
		userEntity.setSalt(new String(salt));
		// setting the right role
		Role role = roleRepo.findById(user.getRoleId()).orElse(null);
		if(role == null)
			throw new BadRequestException("Role Id is invalid");
		userEntity.setRole(role);
		userEntity = userRepo.save(userEntity);
		user.setId(userEntity.getId());
		user.setPassword(null);
		return user;
	}
	
	public String authenticate(LoginRequest request) throws Exception {
		User user = userRepo.findByUserName(request.getUserName());
		if(user == null)
			throw new UnAuthenticatedException("Invalid Login");
		boolean verified = PasswordUtil.verifyPassword(request.getPassword(), user.getPassword(), user.getSalt());
		if(!verified)
			throw new UnAuthenticatedException("Invalid Login");
		// create a JWT
		return JwtService.generateJWT(user);
	}
	
}
