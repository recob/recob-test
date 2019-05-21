package com.recob.service.statistic;

import com.recob.domain.AnswerStatistic;
import com.recob.domain.holder.SurveyHolder;
import com.recob.map.RecobUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class StatisticService implements IStatisticService {

    private final DirectProcessor<AnswerStatistic> stream = DirectProcessor.create();
    private final AtomicInteger finishedUserCount = new AtomicInteger(0);

    private RecobUserRepository userRepository;

    @Override
    public void registerAnswer(long questionId) {
        if (SurveyHolder.getSurvey().getQuestions().size() == questionId + 1) {
            stream.sink()
                    .next(new AnswerStatistic(userRepository.count(), finishedUserCount.incrementAndGet()));
        }

    }

    @Override
    public Flux<AnswerStatistic> stream() {
        return stream;
    }
}
