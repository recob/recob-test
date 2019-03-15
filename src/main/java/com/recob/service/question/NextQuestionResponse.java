package com.recob.service.question;

import com.recob.domain.question.QuestionType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class NextQuestionResponse {
    private @NotNull  String       value;
    private @NotNull  Long         position;
    private @NotNull  QuestionType type;
    private @Nullable List<QuestionOptionResponse> options;
}
