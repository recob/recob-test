package com.recob.controller.register.dto;

import lombok.Data;

@Data
public class RegisterApplicationRequest {
    private String host;
    private Long   port;
    private String uuid;
}
