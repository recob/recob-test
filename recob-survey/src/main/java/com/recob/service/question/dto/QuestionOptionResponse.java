package com.recob.service.question.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QuestionOptionResponse {
    private @NotNull Object value;
    private @NotNull String id;
}
