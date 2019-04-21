package com.recob.controller.login;

import com.recob.controller.login.dto.RequestAuth;
import com.recob.domain.user.RecobUser;
import com.recob.repository.RecobUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@AllArgsConstructor
@CrossOrigin
public class LoginController {

    private RecobUserRepository userRepository;

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody RequestAuth requestAuth) {

        RecobUser savedUser = saveUser(requestAuth);

        HttpCookie cookie = ResponseCookie.from("Authorization", savedUser.getId())
                .maxAge(60_000_000_000L)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    private RecobUser saveUser(@RequestBody RequestAuth requestAuth) {
        RecobUser user = new RecobUser();

        user.setId(UUID.randomUUID().toString());
        user.setName(requestAuth.getName());

        return userRepository.save(user);
    }
}
