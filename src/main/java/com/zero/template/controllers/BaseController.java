package com.zero.template.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.zero.template.core.auth.RequestContext;
import com.zero.template.core.auth.UserProfile;

@RestController
public abstract class BaseController {
	
	@Autowired
	private RequestContext context;
	
	protected Long getUserId() {
		UserProfile userProfile = context.getUserProfile();
		return userProfile != null ? userProfile.getUserId() : null;
	}
	
	protected UserProfile getUserProfile() {
		return context.getUserProfile();
	}

}
