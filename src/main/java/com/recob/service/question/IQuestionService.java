package com.recob.service.question;

import com.recob.controller.ws.dto.AnswerMessage;
import com.recob.domain.question.Question;
import com.recob.service.question.dto.NextQuestionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionService {

    Mono<Void> getNextQuestion(Flux<AnswerMessage> inbound);

    Flux<NextQuestionResponse> stream();

    /**
     * return first question
     * @return first question
     */
    NextQuestionResponse getFirstQuestion();
}
