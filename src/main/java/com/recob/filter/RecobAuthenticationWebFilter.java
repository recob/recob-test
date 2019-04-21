package com.recob.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RecobAuthenticationWebFilter extends AuthenticationWebFilter {
    public RecobAuthenticationWebFilter() {
        super(Mono::just);
    }

    @Override
    @Autowired
    public void setServerAuthenticationConverter(ServerAuthenticationConverter authenticationConverter) {
        super.setServerAuthenticationConverter(authenticationConverter);
    }
}
