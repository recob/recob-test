package com.recob.service.user;

import com.recob.domain.user.RecobUser;
import com.recob.repository.RecobUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("userDetailsService")
@AllArgsConstructor
public class RecobUserService implements IRecobUserService {

    private RecobUserRepository userRepository;

    @Override
    public RecobUser saveUser(RecobUser user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<UserDetails> findByUsername(String s) {
        return userRepository.findById(s)
                .map(u -> (UserDetails) u)
                .map(Mono::just)
                .orElse(Mono.empty());
    }
}
