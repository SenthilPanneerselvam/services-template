package com.zero.template.constants;

import com.zero.template.entities.User;

public class AppConstants {

	public static final User SYSTEM_USER;
	
	static {
		SYSTEM_USER = new User();
		SYSTEM_USER.setId(1l);
		SYSTEM_USER.setActive(true);
		SYSTEM_USER.setUserName("System User");
	}
	
	// constants used in header and jwt token
	public static final String AUTH_HEADER = "authorization";
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "userName";
	public static final String ROLE_CODE = "roleCode";
	
}
