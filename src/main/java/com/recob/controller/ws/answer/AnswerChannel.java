package com.recob.controller.ws.answer;

import com.recob.controller.ws.answer.dto.AnswerMessage;
import com.recob.controller.ws.answer.mapper.AnswerMessageMessageMapper;
import com.recob.service.question.IQuestionService;
import com.recob.service.question.QuestionService;
import com.recob.service.starter.ITestStarter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * main stream between client and backend
 * pushing first question to user
 * on each answer getting next question
 */

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AnswerChannel implements WebSocketHandler {

    private AnswerMessageMessageMapper      mapper;
    private ConfigurableListableBeanFactory beanFactory;
    private ITestStarter                    testStarter;
    private AtomicInteger                   userCounter;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session
                .receive()
                .map(WebSocketMessage::retain)
                .map(WebSocketMessage::getPayload)
                .publishOn(Schedulers.parallel())
                .transform(mapper::decode)
                .log()
                .transform(this::handleMessage)
                .transform(s -> mapper.encode(s, session.bufferFactory()))
                .map(db -> new WebSocketMessage(WebSocketMessage.Type.TEXT, db))
                .as(session::send);
    }

    private Flux<?> handleMessage(Flux<AnswerMessage> inbound) {

        userCounter.addAndGet(1);

        IQuestionService questionService = beanFactory.createBean(QuestionService.class);

        return Flux.merge(
                questionService.getNextQuestion(inbound),
                questionService.stream(),
                testStarter.stream()
        );
    }

}
