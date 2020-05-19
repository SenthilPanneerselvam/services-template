package com.zero.template.core.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * A request scoped bean to store the user realted context and also other 
 * request related context can be saved here.
 * 
 * @author senthilkumarpanneerselvam
 *
 */
@Component
@RequestScope
public class RequestContext {
	
	private UserProfile userProfile;

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
}
