package com.recob.service;

import reactor.core.publisher.Mono;

public interface IApplicationManager {

    Mono<CreateApplicationResponse> createApplication(String surveyId);

    void registerApplication(String host, Long port, String applicationUUID);
}
