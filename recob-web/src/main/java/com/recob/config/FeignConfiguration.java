package com.recob.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.recob.client")
@Configuration
public class FeignConfiguration {
}
