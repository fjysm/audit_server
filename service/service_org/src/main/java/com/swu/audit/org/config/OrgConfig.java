package com.swu.audit.org.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.swu.audit.org.mapper")
public class OrgConfig {
}
