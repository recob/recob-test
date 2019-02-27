package com.recob.controller.ws.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AnswerMessage {
    private @NotNull Long   questionId;
    private @NotNull String answerId;
}
