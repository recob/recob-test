package com.recob.controller.ws;

import com.recob.controller.ws.dto.AnswerMessage;
import com.recob.service.answer.AnswerService;
import com.recob.service.answer.IAnswerService;
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

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AnswerChannel implements WebSocketHandler {

    private AnswerMessageMessageMapper      mapper;
    private ConfigurableListableBeanFactory beanFactory;

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

        IAnswerService answerService = beanFactory.createBean(AnswerService.class);

        return Flux.merge(
                answerService.saveAnswer(inbound),
                answerService.stream()
        );
    }

}
