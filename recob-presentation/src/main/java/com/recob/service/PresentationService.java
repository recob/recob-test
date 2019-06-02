package com.recob.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class PresentationService implements IPresentationService {

    private final Map<String, DirectProcessor<Object>> streamMap = new HashMap<>();

    @Override
    public Mono<Void> handle(Flux<?> inbound, String uuid) {
        return inbound
                .doOnNext(o -> sendMessage(o, uuid))
                .then();
    }

    private void sendMessage(Object o, String uuid) {
        if (streamMap.containsKey(uuid)) {
            streamMap.get(uuid).sink().next(o);
        }
    }

    @Override
    public Flux<?> stream(String uuid) {
        streamMap.computeIfAbsent(uuid, key -> streamMap.put(key, DirectProcessor.create()));

        return streamMap.get(uuid);
    }
}
