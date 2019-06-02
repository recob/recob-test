package com.recob.controller.ws;

import com.recob.service.IPresentationService;
import com.recob.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class DisplayChannel implements WebSocketHandler {

    private IPresentationService presentationService;
    private MessageMapper        mapper;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        String uuid = Utils.getUUID(session.getHandshakeInfo().getUri().toString());

        return session
                .receive()
                .map(WebSocketMessage::retain)
                .map(WebSocketMessage::getPayload)
                .publishOn(Schedulers.parallel())
                .transform(mapper::decode)
                .log()
                .transform(i -> handleMessage(uuid))
                .transform(s -> mapper.encode(s, session.bufferFactory()))
                .map(db -> new WebSocketMessage(WebSocketMessage.Type.TEXT, db))
                .as(session::send);
    }

    private Flux<?> handleMessage(String uuid) {

        return Flux.merge(
                presentationService.stream(uuid)
        );
    }
}
