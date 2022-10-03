package com.swu.audit.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.swu")
public class ServiceOrgApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrgApplication.class, args);
    }
}
