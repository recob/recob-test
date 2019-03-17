package com.recob.domain.question;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Class contains information about
 * one answer value to specific question
 *
 * {@link QuestionOption#value} answer value
 * {@link QuestionOption#right} is answer right
 */

@Data
@NoArgsConstructor
public class QuestionOption {

    private @NotNull String  id;
    private @NotNull Object  value;
    private @NotNull boolean right;
}