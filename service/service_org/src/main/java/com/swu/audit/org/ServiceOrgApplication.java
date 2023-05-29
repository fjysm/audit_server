package com.swu.audit.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = "com.swu")
@EnableWebMvc
public class ServiceOrgApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrgApplication.class, args);
    }
}
