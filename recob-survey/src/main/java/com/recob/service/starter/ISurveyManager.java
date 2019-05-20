package com.recob.service.starter;

import com.recob.service.question.dto.NextQuestionResponse;
import reactor.core.publisher.Flux;

public interface ISurveyManager {

    void startSurvey();

    void stopSurvey();

    Flux<NextQuestionResponse> stream();
}
