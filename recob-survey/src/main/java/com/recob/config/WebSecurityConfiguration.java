package com.recob.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
public class WebSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //@formatter:off
        http.csrf()
                .disable()
                .httpBasic()
                .and()
                .cors()
                .and()
            .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .pathMatchers("/dashboard").permitAll()
                .pathMatchers("/survey/start").permitAll()
                .pathMatchers("/survey/stop").permitAll()
                .pathMatchers("/auth").permitAll()
                .anyExchange().authenticated();
        //@formatter:on
        return http.build();
    }
}
