package com.recob.service.launch;

import com.recob.domain.launch.SurveyLaunch;
import com.recob.service.IBaseService;
import reactor.core.publisher.Flux;

public interface ISurveyLaunchService extends IBaseService<SurveyLaunch, String> {
    Flux<SurveyLaunch> findBySurveyId(String surveyId);
}
