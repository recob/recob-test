package com.recob.domain.launch;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserQuestionOption {

    private @NotNull String  id;
    private @NotNull Object  value;
    private @NotNull boolean right;
    private @NotNull boolean userAnswer;
}
