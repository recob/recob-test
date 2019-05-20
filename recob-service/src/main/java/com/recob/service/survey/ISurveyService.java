package com.recob.service.survey;

import com.recob.domain.survey.Survey;
import com.recob.service.IBaseService;
import reactor.core.publisher.Flux;

public interface ISurveyService extends IBaseService<Survey, String> {

    Flux<Survey> findByTitle(String name);
}
