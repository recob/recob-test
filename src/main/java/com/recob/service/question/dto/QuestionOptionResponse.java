package com.recob.service.question.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class QuestionOptionResponse {
    private @NotNull Object value;
    private @NotNull String id;
}
