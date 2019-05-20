package com.recob.service.question.dto;

import com.recob.domain.survey.QuestionType;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class NextQuestionResponse {
    private @NotNull  String       title;
    private @NotNull  String       id;
    private @NotNull  QuestionType type;
    private @Nullable List<QuestionOptionResponse> options;
}
