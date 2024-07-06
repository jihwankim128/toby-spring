package com.toby.config.autoconfig;

import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.toby.config.MyAutoConfiguration;

@MyAutoConfiguration
public class ServerPropertiesConfig {

	@Bean
	public ServerProperties serverProperties(final Environment environment) {
		return Binder.get(environment).bind("", ServerProperties.class).get();
	}

}
