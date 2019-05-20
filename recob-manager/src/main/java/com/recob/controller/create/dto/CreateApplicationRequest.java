package com.recob.controller.create.dto;

import com.recob.domain.ApplicationType;
import lombok.Data;

@Data
public class CreateApplicationRequest {
    private ApplicationType type;
}
