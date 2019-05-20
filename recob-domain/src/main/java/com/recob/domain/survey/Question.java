package com.recob.domain.survey;

import lombok.Data;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Class with information about question
 * and options to it
 *
 * {@link Question#position} question id in test
 * {@link Question#type} question type
 * {@link Question#options} question options if type IS NOT {@link QuestionType#OPEN}
 */

@Data
public class Question {
    private @NotNull  String       title;
    private @NotNull  long         position;
    private @NotNull  QuestionType type;
    private @Nullable List<QuestionOption> options;
}
