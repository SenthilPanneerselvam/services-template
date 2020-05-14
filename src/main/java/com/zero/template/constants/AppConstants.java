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
	
}
