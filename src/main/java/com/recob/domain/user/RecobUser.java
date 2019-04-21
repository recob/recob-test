package com.recob.domain.user;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.security.Principal;

@Data
public class RecobUser implements Principal {
    private @Id String id;
    private     String name;

    @Override
    public String getName() {
        return id;
    }
}
