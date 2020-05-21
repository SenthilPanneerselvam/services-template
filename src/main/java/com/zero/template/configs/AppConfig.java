package com.zero.template.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class AppConfig {

	@Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setLocation(new ClassPathResource("git.properties"));
        c.setIgnoreResourceNotFound(true);
        c.setIgnoreUnresolvablePlaceholders(true);
        return c;
    }
	
}
