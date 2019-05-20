package com.recob.controller.ws.answer;

import com.recob.controller.ws.answer.dto.AnswerMessage;
import com.recob.controller.ws.answer.mapper.AnswerMessageMapper;
import com.recob.service.question.QuestionService;
import com.recob.service.starter.ISurveyManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * main stream between client and backend
 * pushing first question to user
 * on each answer getting next question
 */

@Service
@Slf4j
@AllArgsConstructor
public class AnswerChannel implements WebSocketHandler {

    private AnswerMessageMapper mapper;
    private QuestionService     questionService;
    private ISurveyManager      surveyManager;

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

        return Flux.merge(
                questionService.getNextQuestion(inbound),
                questionService.stream(),
                questionService.getStartedQuestions(),
                surveyManager.stream()
        );
    }

}
