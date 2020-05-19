package com.zero.template.core.auth;

import java.util.List;

import com.zero.template.core.JwtClaims;
import com.zero.template.dtos.PrivilegeDTO;
import com.zero.template.dtos.RoleDTO;
import com.zero.template.dtos.UserDTO;

/**
 * A separate class from userdto/user entity, as this can hold other information that can be 
 * used through out the application such as subscription details or any other information that 
 * will come in handy during domain implementation.
 * 
 * @author senthilkumarpanneerselvam
 *
 */
public class UserProfile {
	
	private String userName;
	
	private String firstName;
	
	private String lastName;
	
	private String countryPhoneCode;
	
	private String mobileNumber;
	
	private Long userId;
	
	private RoleDTO role;
	
	private List<PrivilegeDTO> privileges;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}

	public List<PrivilegeDTO> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<PrivilegeDTO> privileges) {
		this.privileges = privileges;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountryPhoneCode() {
		return countryPhoneCode;
	}

	public void setCountryPhoneCode(String countryPhoneCode) {
		this.countryPhoneCode = countryPhoneCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public void initProfile(UserDTO user) {
		this.userId = user.getId();
		this.userName = user.getUserName();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.countryPhoneCode = user.getCountryPhoneCode();
		this.mobileNumber = user.getMobileNumber();
		
	}

}
