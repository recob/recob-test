package com.recob.domain.model;

import lombok.Data;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SurveyResponse {
    private @Nullable long           availableTime;
    private @NotNull  String         title;
    private @NotNull  String         id;
    private @Nullable String         thumbnail;
    private @NotNull  List<QuestionModel> questions;
}
