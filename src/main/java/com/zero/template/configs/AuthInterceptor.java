package com.zero.template.configs;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.zero.template.constants.AppConstants;
import com.zero.template.core.JwtClaims;
import com.zero.template.core.OpenEndPoint;
import com.zero.template.core.UnAuthenticatedException;
import com.zero.template.core.auth.RequestContext;
import com.zero.template.core.auth.UserProfile;
import com.zero.template.services.JwtService;
import com.zero.template.services.UserService;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	
	@Autowired
	private RequestContext context;
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// I do not think we need to check for public annotation at the class level at this point in time
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			OpenEndPoint methodAnnotation = handlerMethod.getMethod().getAnnotation(OpenEndPoint.class);
			if(methodAnnotation != null) {
				return true;
			}
		} else {
			throw new RuntimeException("Error in obtaining handler in auth interceptor");
		}
			
		// retrieve the jwt token
		String jws = request.getHeader(AppConstants.AUTH_HEADER);
		if(jws != null) {
			// parse the token
			Optional<JwtClaims> claimsObj = JwtService.parseJWT(jws);
			
			if(!claimsObj.isEmpty()) {
				Long userId = Long.parseLong(claimsObj.get().getUserId());
				context.setUserProfile(getUserProfile(userId));
				return true;
			}
		}
		throw new UnAuthenticatedException("Missing / Invalid token");
	}
	
	@Cacheable
	private UserProfile getUserProfile(Long userId) {
		return userService.getUserProfile(userId);
	}

}
