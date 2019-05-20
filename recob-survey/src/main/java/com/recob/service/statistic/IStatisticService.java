package com.recob.service.statistic;

import com.recob.domain.AnswerStatistic;
import reactor.core.publisher.Flux;

public interface IStatisticService {

    void registerAnswer(long questionId);

    Flux<AnswerStatistic> stream();
}
