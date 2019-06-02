package com.recob.controller.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class MessageMapper {

    private Jackson2JsonEncoder encoder;
    private Jackson2JsonDecoder decoder;

    public MessageMapper(ObjectMapper mapper) {
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

    public Flux<?> decode(Flux<DataBuffer> inbound) {
        //@formatter:off
        return inbound.flatMap(p -> decoder.decode(
                        Mono.just(p),
                        ResolvableType.forType(new ParameterizedTypeReference<Object>() {
                        }),
                        MediaType.APPLICATION_JSON,
                        Collections.emptyMap()
                ))
                .map(o -> o);
        //@formatter:on
    }
}
