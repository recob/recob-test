package com.recob.service.question;

import com.recob.controller.ws.answer.dto.AnswerMessage;
import com.recob.service.question.dto.NextQuestionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * service contains business logic
 * about question
 *
 * should be one for per user
 *
 */

public interface IQuestionService {

    /**
     * pushing next question
     * into {@link IQuestionService#stream()}
     * for current answer
     * @param inbound inbound stream with answers
     */
    Mono<Void> getNextQuestion(Flux<AnswerMessage> inbound);

    /**
     * stream with questions for current user
     * @return stream
     */
    Flux<NextQuestionResponse> stream();
}
