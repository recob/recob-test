package com.recob.controller.ws.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * question answer from ui
 */

@Data
@NoArgsConstructor
public class AnswerMessage {
    private @NotNull long         id;
    private @NotNull List<String> answer;
}
