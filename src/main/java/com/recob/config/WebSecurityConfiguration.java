package com.recob.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.ArrayList;
import java.util.UUID;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        //@formatter:off
        http
            .addFilterAt(authFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .authorizeExchange()
                .anyExchange().permitAll();
        //@formatter:on
        return http.build();
    }


    @Bean
    public AuthenticationWebFilter authFilter() {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(Mono::just);
        String userId = UUID.randomUUID().toString();
        authenticationWebFilter.setServerAuthenticationConverter(exchange -> {
            AnonymousAuthenticationToken token = new AnonymousAuthenticationToken("key", (Principal) () -> userId, new ArrayList<GrantedAuthority>() {{
                add((GrantedAuthority) () -> "PETYA");
            }});

            return Mono.just(token);
        });

        return authenticationWebFilter;
    }

}
