 package com.moneycore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@EnableSwagger2
public class ProfileManagement {

	private static final Logger logger = LoggerFactory.getLogger(ProfileManagement.class);

	public static void main(String[] args) {
		SpringApplication.run(ProfileManagement.class, args);
		logger.info("profile management Application Started........");
	}
}
