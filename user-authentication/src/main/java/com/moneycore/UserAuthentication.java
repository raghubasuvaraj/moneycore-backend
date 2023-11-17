package com.moneycore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class UserAuthentication {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthentication.class);

    public static void main(String[] args) {
        SpringApplication.run(UserAuthentication.class, args);
        logger.info("user UserAuthentication Application Started........run run");
    }
}
