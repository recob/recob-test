package com.recob.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateApplicationResponse {
    private String host;
    private Long port;
}
