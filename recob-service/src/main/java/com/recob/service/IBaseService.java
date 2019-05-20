package com.recob.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBaseService<T, ID> {

    Flux<T> findAll();

    Mono<T> findById(ID id);

    Mono<T> save(T entity);
}
