package com.recob.domain.model;

import com.recob.domain.survey.Question;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Schema for creating test
 */

@Data
public class SurveyModel {
    private @Nullable long           availableTime;
    private @NotNull  String         title;
    private @NotNull  List<Question> questions;
    private @NotNull  String         thumbnail;
}
