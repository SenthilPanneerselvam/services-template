package com.zero.template.configs;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigs {
	
	@Bean
	public DozerBeanMapper getBeanMapper() {
		DozerBeanMapper beanMapper = new DozerBeanMapper();
		return beanMapper;
	} 

}
