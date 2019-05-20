package com.recob.config;

import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Configuration
public class KubernetesConfig {

    @Bean
    @Profile("!dev")
    public CoreV1Api api() throws IOException {
        return new CoreV1Api(Config.fromCluster());
    }

    @Bean
    @Profile("dev")
    public CoreV1Api devApi() throws IOException {
        return new CoreV1Api(Config.defaultClient());
    }
}
