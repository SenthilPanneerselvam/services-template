package com.zero.template.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zero.template.core.BadRequestException;
import com.zero.template.core.BeanMapper;
import com.zero.template.core.NotFoundException;
import com.zero.template.core.UnAuthenticatedException;
import com.zero.template.core.auth.UserProfile;
import com.zero.template.dtos.LoginRequest;
import com.zero.template.dtos.PrivilegeDTO;
import com.zero.template.dtos.RoleDTO;
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
	
	@Transactional
	// TODO: add this in a common place
	public UserProfile getUserProfile(Long userId) {
		UserProfile userProfile = new UserProfile();
		Optional<User> user = userRepo.findById(userId);
		UserDTO userdto = getUserDTO(user.get());
		userProfile.initProfile(userdto);
		userProfile.setRole(BeanMapper.map(user.get().getRole(), RoleDTO.class));
		userProfile.setPrivileges(BeanMapper.map(user.get().getRole().getPrivileges(), PrivilegeDTO.class));
		return userProfile;
	}
	
	public String authenticate(LoginRequest request) throws Exception {
		User user = userRepo.findByUserName(request.getUserName());
		if(user == null || !user.getActive())
			throw new UnAuthenticatedException("Invalid Login");
		boolean verified = PasswordUtil.verifyPassword(request.getPassword(), user.getPassword(), user.getSalt());
		if(!verified)
			throw new UnAuthenticatedException("Invalid Login");
		// create a JWT
		return JwtService.generateJWT(user);
	}
	
	public UserDTO getUser(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if(user.isEmpty())
			throw new NotFoundException("User Not found");
		return getUserDTO(user.get());
	}
	
	private UserDTO getUserDTO(User user) {
		UserDTO userDTO = BeanMapper.map(user, UserDTO.class);
		userDTO.setPassword(null);
		userDTO.setRoleId(user.getRole().getId());
		return userDTO;
	}
	
}
