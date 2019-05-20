package com.recob.controller.ws.answer.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * question answer from ui
 */

@Data
public class AnswerMessage {
    private @NotNull long         id;
    private @NotNull List<String> answer;
}
