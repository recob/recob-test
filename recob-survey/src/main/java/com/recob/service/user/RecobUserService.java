package com.recob.service.user;

import com.recob.domain.user.RecobUser;
import com.recob.map.RecobUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@AllArgsConstructor
public class RecobUserService implements IRecobUserService {

    private RecobUserRepository userRepository;

    @Override
    public RecobUser saveUser(RecobUser user) {
        return userRepository.save(user);
    }

    @Override
    public List<RecobUser> findAll() {
        List<RecobUser> result = new ArrayList<>();

        userRepository.findAll().iterator().forEachRemaining(result::add);

        return result;
    }

    @Override
    public Mono<UserDetails> findByUsername(String s) {
        return userRepository.findById(s)
                .map(u -> (UserDetails) u)
                .map(Mono::just)
                .orElse(Mono.empty());
    }
}
