package com.recob.controller.login;

import com.recob.domain.answer.UserAnswer;
import com.recob.domain.user.RecobUser;
import com.recob.map.AnswerRepository;
import com.recob.service.statistic.IStatisticService;
import com.recob.service.user.IRecobUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;


@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin
public class LoginController {

    private IRecobUserService userService;
    private AnswerRepository  answerRepository;
    private IStatisticService statisticService;

    @GetMapping("/auth")
    public RecobUser auth(@RequestParam String name) {
        log.info("[auth] auth new user {}", name);
        return saveUser(name);
    }

    private RecobUser saveUser(@RequestBody String name) {
        RecobUser user = new RecobUser();

        user.setId(UUID.randomUUID().toString());
        user.setName(name);

        log.info("[saveUser] saving answer");
        answerRepository.save(new UserAnswer(user.getId(), new HashMap<>()));
        log.info("[saveUser] saving user");
        user = userService.saveUser(user);
        log.info("[saveUser] registering user ");
        statisticService.registerUser();
        log.info("[saveUser] returning user");
        return user;
    }
}
