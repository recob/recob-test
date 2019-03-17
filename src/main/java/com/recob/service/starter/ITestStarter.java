package com.recob.service.starter;

import com.recob.service.question.dto.NextQuestionResponse;
import reactor.core.publisher.Flux;

public interface ITestStarter {
    void startTest();

    Flux<NextQuestionResponse> stream();
}
