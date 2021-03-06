package com.recob.service.user;

import com.recob.domain.user.RecobUser;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

import java.util.List;

public interface IRecobUserService extends ReactiveUserDetailsService {

    RecobUser saveUser(RecobUser user);

    List<RecobUser> findAll();
}
