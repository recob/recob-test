package com.recob.service.statistic;

import com.recob.domain.AnswerStatistic;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStatisticService {

    void registerAnswer(long questionId);

    void registerUser();

    Mono<?> getStartedValues();

    Flux<AnswerStatistic> stream();
}
