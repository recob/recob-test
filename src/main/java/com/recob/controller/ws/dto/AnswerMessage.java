package com.recob.controller.ws.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * question answer from ui
 */

@Data
@NoArgsConstructor
public class AnswerMessage {
    private @NotNull long   questionId;
    private @NotNull String answer;
}
