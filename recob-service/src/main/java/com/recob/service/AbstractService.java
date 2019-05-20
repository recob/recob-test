package com.recob.service;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractService<T, ID, R extends ReactiveMongoRepository<T, ID>> implements IBaseService<T, ID> {

    protected R repository;

    @Override
    public Flux<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public Mono<T> save(T entity) {
        return repository.save(entity);
    }
}
