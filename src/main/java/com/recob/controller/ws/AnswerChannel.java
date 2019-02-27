package com.recob.controller.ws;

import com.recob.controller.ws.dto.AnswerMessage;
import com.recob.service.answer.IAnswerService;
import com.recob.service.question.IQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import java.security.Principal;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AnswerChannel implements WebSocketHandler {

    private AnswerMessageMessageMapper      mapper;
    private IAnswerService                  answerService;
    private IQuestionService                questionService;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session
                .receive()
                .map(WebSocketMessage::retain)
                .map(WebSocketMessage::getPayload)
                .publishOn(Schedulers.parallel())
                .transform(mapper::decode)
                .log()
                .zipWith(ReactiveSecurityContextHolder.getContext())
                .doOnEach(this::saveAnswer)
                .map((tuple) -> questionService.getNextQuestion(tuple.getT1().getQuestionId()))
                .onBackpressureBuffer()
                .transform(s -> mapper.encode(s, session.bufferFactory()))
                .map(db -> new WebSocketMessage(WebSocketMessage.Type.TEXT, db))
                .as(session::send);
    }

    private void saveAnswer(Signal<Tuple2<AnswerMessage, SecurityContext>> signal) {
        Tuple2<AnswerMessage, SecurityContext> tuple = signal.get();

        if (tuple != null) {
            answerService.saveAnswer(tuple.getT1().getQuestionId(),
                    tuple.getT1().getAnswerId(),
                    ((Principal) tuple.getT2().getAuthentication().getPrincipal()).getName());
        }
    }

}
