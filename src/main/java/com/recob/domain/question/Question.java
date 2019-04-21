package com.recob.domain.question;

import lombok.Data;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Class with information about question
 * and questionOptions to it
 *
 * {@link Question#position} question id in test
 * {@link Question#questionType} question type
 * {@link Question#questionOptions} question questionOptions if type IS NOT {@link QuestionType#OPEN}
 */

@Data
public class Question {
    private @NotNull  String       title;
    private @NotNull  long         position;
    private @NotNull  QuestionType questionType;
    private @Nullable List<QuestionOption> questionOptions;
}
