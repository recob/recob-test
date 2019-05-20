package com.recob.controller.login;

import com.recob.domain.answer.UserAnswer;
import com.recob.domain.user.RecobUser;
import com.recob.map.AnswerRepository;
import com.recob.service.user.IRecobUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;


@RestController
@AllArgsConstructor
@CrossOrigin
public class LoginController {

    private IRecobUserService userService;
    private AnswerRepository  answerRepository;

    @GetMapping("/auth")
    public RecobUser auth(@RequestParam String name) {

        return saveUser(name);
    }

    private RecobUser saveUser(@RequestBody String name) {
        RecobUser user = new RecobUser();

        user.setId(UUID.randomUUID().toString());
        user.setName(name);

        answerRepository.save(new UserAnswer(user.getId(), new HashMap<>()));

        return userService.saveUser(user);
    }
}
