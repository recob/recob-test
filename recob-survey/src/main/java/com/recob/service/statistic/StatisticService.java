package com.recob.service.statistic;

import com.recob.domain.AnswerStatistic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

@Service
public class StatisticService implements IStatisticService {

    private DirectProcessor<AnswerStatistic> stream = DirectProcessor.create();

    @Override
    public void registerAnswer(long questionId) {
        stream.sink()
                .next(new AnswerStatistic(String.valueOf(questionId)));

    }

    @Override
    public Flux<AnswerStatistic> stream() {
        return stream;
    }
}
