package com.recob.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@Configuration
@EnableMapRepositories(basePackages = "com.recob.repository")
public class RepositoryConfiguration {
}
