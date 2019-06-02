package com.recob.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPresentationService {

    Mono<Void> handle(Flux<?> inbound, String uuid);

    Flux<?> stream(String uuid);
}
