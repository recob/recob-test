package com.recob.domain.question;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Class contains information about
 * one answer value to specific question
 *
 * {@link Option#value} answer value
 * {@link Option#right} is answer right
 */

@Data
@NoArgsConstructor
public class Option {

    private @NotNull String  id;
    private @NotNull Object  value;
    private @NotNull boolean right;
}
