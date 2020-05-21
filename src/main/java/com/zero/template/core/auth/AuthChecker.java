package com.zero.template.core.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zero.template.core.ForbiddenException;

@Aspect
@Component
public class AuthChecker {

	@Autowired
	private RequestContext context;

	@Before("@annotation(com.zero.template.core.auth.HasRole)")
	public void hasRole(JoinPoint joinPoint) {
		if (joinPoint.getSignature() instanceof MethodSignature) {
			MethodSignature ms = (MethodSignature) joinPoint.getSignature();
			HasRole hasRole = ms.getMethod().getAnnotation(HasRole.class);
			String[] allowedRoles = hasRole.value();
			UserProfile userProfile = context.getUserProfile();
			String userRoleCode = userProfile.getRole() != null ? userProfile.getRole().getCode() : null;
			boolean allowed = false;
			for (String allowedRole : allowedRoles) {
				if (allowedRole.equalsIgnoreCase(userRoleCode)) {
					allowed = true;
					break;
				}
			}
			if (!allowed)
				throw new ForbiddenException("User does not have the needed role to execute this operation");
		}

	}

	@Before("@annotation(com.zero.template.core.auth.HasPrivilege)")
	public void hasPrivilege(JoinPoint joinPoint) {
		if (joinPoint.getSignature() instanceof MethodSignature) {
			MethodSignature ms = (MethodSignature) joinPoint.getSignature();
			HasPrivilege hasPrivilege = ms.getMethod().getAnnotation(HasPrivilege.class);
			String[] allowedPrivileges = hasPrivilege.value();
			UserProfile userProfile = context.getUserProfile();
			List<String> userPrivilegeCodes = userProfile.getPrivileges() == null ? null
					: (userProfile.getPrivileges().stream().map((privilege) -> {
						return privilege.getCode();
					}).collect(Collectors.toList()));

			boolean allowed = false;
			for(String allowedPrivilege : allowedPrivileges) {
				allowed = userPrivilegeCodes.stream().anyMatch(upc -> allowedPrivilege.equals(upc));
				if(allowed) {
					break;
				}
			}
			if (!allowed)
				throw new ForbiddenException("User does not have the needed privilege to execute this operation");
		}

	}

}
