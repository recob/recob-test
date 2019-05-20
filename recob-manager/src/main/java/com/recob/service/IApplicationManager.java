package com.recob.service;

import com.recob.domain.ApplicationType;
import reactor.core.publisher.Mono;

public interface IApplicationManager {

    Mono<CreateApplicationResponse> createApplication(ApplicationType type);

    void registerApplication(String host, Long port, String applicationUUID);
}
