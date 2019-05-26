package com.recob.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecobStarterConfiguration {

    @Bean
    public RegisterApplicationListener registerApplicationListener() {
        return new RegisterApplicationListener();
    }
}
