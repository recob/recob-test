package com.recob.service.answer;

import com.recob.controller.ws.dto.AnswerMessage;
import com.recob.service.question.NextQuestionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * service is used to save answer from user
 */
public interface IAnswerService {


    /**
     * save answer
     * and return next question
     *
     * @param inbound inbound stream
     * @return next question
     */
    Mono<Void> saveAnswer(Flux<AnswerMessage> inbound);

    /**
     * stream with next questions to user
     * @return stream
     */
    Flux<NextQuestionResponse> stream();
}
