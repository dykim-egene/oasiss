package com.oasis;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.oasis.config.SessionListener;

@SpringBootApplication
public class OasisApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(OasisApplication.class);
	}

	@Bean
	public HttpSessionListener httpSessionListener() {
		return new SessionListener();
	}

	public static void main(String[] args) {
		SpringApplication.run(OasisApplication.class, args);
	}

}
