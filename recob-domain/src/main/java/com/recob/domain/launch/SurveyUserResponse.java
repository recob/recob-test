package com.recob.domain.launch;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SurveyUserResponse {
    private @NotNull String title;
    private @NotNull String thumbnail;
    private @NotNull List<UserQuestionModel> questions;
}
