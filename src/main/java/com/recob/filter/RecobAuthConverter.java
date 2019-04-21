package com.recob.filter;

import com.recob.repository.RecobUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class RecobAuthConverter implements ServerAuthenticationConverter {

    private RecobUserRepository userRepository;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        List<HttpCookie> cookies = exchange.getRequest().getCookies().get("Authorization");

        if (!CollectionUtils.isEmpty(cookies) && cookies.size() == 1) {

            String userId = cookies.get(0).getValue();

            if (userRepository.existsById(userId)) {

                AnonymousAuthenticationToken token =
                        new AnonymousAuthenticationToken("key",
                                (Principal) () -> userId,
                                new ArrayList<GrantedAuthority>() {{
                                    add((GrantedAuthority) () -> "PETYA");
                                }});

                return Mono.just(token);
            }
        }
        return Mono.empty();
    }
}
