package com.recob.domain.holder;


import com.recob.domain.survey.Survey;

import javax.validation.constraints.NotNull;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class holds single instance of test survey
 *
 * {@link SurveyHolder#survey} test survey for current application instance
 */

public class SurveyHolder {

    private static @NotNull Survey survey;

    private static AtomicBoolean surveyStarted = new AtomicBoolean();

    public static Survey getSurvey() {
        return survey;
    }

    public static Survey setSurvey(Survey survey) {
        SurveyHolder.survey = survey;
        return survey;
    }

    public static boolean hasSurveyStarted() {
        return surveyStarted.get();
    }

    public static void startSurvey() {
        surveyStarted.set(true);
    }
}
