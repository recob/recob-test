package com.recob.domain.model;

import com.recob.domain.survey.QuestionOption;
import com.recob.domain.survey.QuestionType;
import lombok.Data;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QuestionModel {
    private @NotNull  String       title;
    private @NotNull  String       id;
    private @NotNull  QuestionType type;
    private @Nullable List<QuestionOption> options;
}
