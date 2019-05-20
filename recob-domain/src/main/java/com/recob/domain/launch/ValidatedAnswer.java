package com.recob.domain.launch;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ValidatedAnswer {
    private @NotNull Long         questionId;
    private @NotNull List<String> answers;
    private @NotNull boolean      right;
}
