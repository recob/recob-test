package com.recob.controller.ws.stats.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recob.controller.ws.answer.dto.AnswerMessage;
import com.recob.domain.AnswerStatistic;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class StatisticMessageMessageMapper {

    private Jackson2JsonEncoder encoder;
    private Jackson2JsonDecoder decoder;

    public StatisticMessageMessageMapper(ObjectMapper mapper) {
        encoder = new Jackson2JsonEncoder(mapper);
        decoder = new Jackson2JsonDecoder(mapper);
    }

    public Flux<DataBuffer> encode(Flux<?> outbound, DataBufferFactory dataBufferFactory) {
        return outbound
                .flatMap(i -> encoder.encode(
                        Mono.just(i),
                        dataBufferFactory,
                        ResolvableType.forType(Object.class),
                        MediaType.APPLICATION_JSON,
                        Collections.emptyMap()
                ));

    }

    public Flux<AnswerStatistic> decode(Flux<DataBuffer> inbound) {
        //@formatter:off
        return inbound.flatMap(p -> decoder.decode(
                        Mono.just(p),
                        ResolvableType.forType(new ParameterizedTypeReference<AnswerStatistic>() {
                        }),
                        MediaType.APPLICATION_JSON,
                        Collections.emptyMap()
                ))
                .map(o -> (AnswerStatistic) o);
        //@formatter:on
    }
}
