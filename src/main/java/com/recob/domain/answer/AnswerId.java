package com.recob.domain.answer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class AnswerId implements Serializable {
    private @NotNull String userId;
    private @NotNull long   questionId;
}
