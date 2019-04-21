package com.recob.config;

import com.recob.filter.RecobAuthenticationWebFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
public class WebSecurityConfiguration {

    private RecobAuthenticationWebFilter authenticationWebFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //@formatter:off
        http.csrf()
                .disable()
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .authorizeExchange()
                .pathMatchers("/stats").permitAll()
                .pathMatchers("/start").permitAll()
                .pathMatchers("/schema").permitAll()
                .pathMatchers("/auth").permitAll()
                .anyExchange().authenticated();
        //@formatter:on
        return http.build();
    }
}
