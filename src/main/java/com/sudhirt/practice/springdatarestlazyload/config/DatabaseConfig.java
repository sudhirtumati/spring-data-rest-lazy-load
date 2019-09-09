package com.sudhirt.practice.springdatarestlazyload.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.sudhirt.practice.springdatarestlazyload.repository")
public class DatabaseConfig {

}