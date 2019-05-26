package com.recob.service.result;

import com.recob.domain.launch.SurveyLaunch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISurveyResultService {

    Mono<SurveyLaunch> validateAnswers();

    Flux<?> stream();
}
