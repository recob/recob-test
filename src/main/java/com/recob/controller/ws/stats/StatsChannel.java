package com.recob.controller.ws.stats;

import com.recob.controller.ws.stats.mapper.StatisticMessageMessageMapper;
import com.recob.service.statistic.IStatisticService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StatsChannel implements WebSocketHandler {

    private IStatisticService statisticService;
    private StatisticMessageMessageMapper mapper;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session
                .receive()
                .map(WebSocketMessage::retain)
                .map(WebSocketMessage::getPayload)
                .publishOn(Schedulers.parallel())
                .log()
                .transform(this::handleMessage)
                .transform(s -> mapper.encode(s, session.bufferFactory()))
                .map(db -> new WebSocketMessage(WebSocketMessage.Type.TEXT, db))
                .as(session::send);
    }

    private Flux<?> handleMessage(Object unused) {
        return statisticService.stream();
    }
}
