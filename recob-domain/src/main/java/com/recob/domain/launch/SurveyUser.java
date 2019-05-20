package com.recob.domain.launch;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class SurveyUser {
    private @NotNull String id;
    private @NotNull String username;
}
