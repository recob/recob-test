package com.recob.domain.launch;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Document
public class SurveyLaunch {
    private @Id               String id;
    private @NotNull @Indexed String surveyId;
    private @NotNull          Date   date;
    private @NotNull          Map<SurveyUser, List<ValidatedAnswer>> answers;
}
