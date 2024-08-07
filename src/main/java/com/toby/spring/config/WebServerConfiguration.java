package com.toby.spring.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration(proxyBeanMethods = false)
public class WebServerConfiguration {

//	@Bean
	public ServletWebServerFactory customerWebServerFactory() {
		final TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.setPort(9090);
		return factory;
	}

}
