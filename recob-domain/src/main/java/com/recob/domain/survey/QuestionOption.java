package com.recob.domain.survey;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Class contains information about
 * one answer value to specific question
 *
 * {@link QuestionOption#value} answer value
 * {@link QuestionOption#right} is answer right
 */

@Data
public class QuestionOption {
    private @NotNull String  id;
    private @NotNull Object  value;
    private @NotNull boolean right;
}
