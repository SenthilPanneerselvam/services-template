package com.zero.template.core.auth;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zero.template.core.ForbiddenException;

@Aspect
@Component
public class RoleCheckAspect {
	
	@Autowired
	private RequestContext context;
	
	@Before("@annotation(com.zero.template.core.auth.HasRole)")
	public void hasRole(JoinPoint joinPoint) {
		if(joinPoint.getSignature() instanceof MethodSignature) {
			MethodSignature ms = (MethodSignature) joinPoint.getSignature();
			HasRole hasRole = ms.getMethod().getAnnotation(HasRole.class);
			String[] allowedRoles = hasRole.value();
			UserProfile userProfile = context.getUserProfile();
			String userRoleCode = userProfile.getRole() != null ? userProfile.getRole().getCode() : null;
			boolean allowed = false;
			for(String allowedRole : allowedRoles) {
				if(allowedRole.equalsIgnoreCase(userRoleCode)) {
					allowed = true;
					break;
				}
			}
			if(!allowed)
				throw new ForbiddenException("User does not have the needed role to execute this operation");
		}
		
	}
	
}
