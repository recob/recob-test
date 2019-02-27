package com.recob.domain.question;

import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Class with information about question
 * and options to it
 *
 * {@link Question#position} question position in test
 * {@link Question#questionType} question type
 * {@link Question#options} question options if type IS NOT {@link QuestionType#OPEN}
 */

@Data
@NoArgsConstructor
public class Question {

    private @NotNull  String       value;
    private @NotNull  long         position;
    private @NotNull  QuestionType questionType;
    private @Nullable List<Option> options;
}
