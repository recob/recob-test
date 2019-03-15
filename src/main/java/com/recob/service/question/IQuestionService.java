package com.recob.service.question;

import com.recob.domain.question.Question;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Mono;

public interface IQuestionService {
    /**
     * return next question if exists
     * @param currentQuestion current question position
     * @return next qustion
     */
    @Nullable
    Question getNextQuestion(long currentQuestion);

    /**
     * return first question
     * @return first question
     */
    Mono<Question> getFirstQuestion();
}
