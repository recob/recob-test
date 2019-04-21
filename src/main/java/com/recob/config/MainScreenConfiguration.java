package com.recob.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class MainScreenConfiguration {

    @Bean
    public AtomicInteger userCounter() {
        return new AtomicInteger();
    }
}
