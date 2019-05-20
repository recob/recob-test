package com.recob.service;

import com.recob.domain.ApplicationType;
import com.recob.holder.PodHolder;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Node;
import io.kubernetes.client.models.V1NodeAddress;
import io.kubernetes.client.models.V1Pod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ApplicationManager implements IApplicationManager {

    private final Map<String, RegisterInfo> registerMap = new HashMap<>();
    private final String INTERNAL_IP = "InternalIP";

    private CoreV1Api api;

    @Override
    public Mono<CreateApplicationResponse> createApplication(ApplicationType type) {
        String uuid = UUID.randomUUID().toString();
        log.info("[createApplication] creating application {}", uuid);

        String podName;
        try {
            V1Pod pod = startApplication(type, uuid);
            podName = pod.getMetadata().getName();

        } catch (ApiException e) {
            log.info("[createApplication] exception while creating application {}", type, e);
            return Mono.empty();
        }

        return Mono.create(sink -> registerMap.put(uuid, new RegisterInfo(sink, podName)));
    }

    @Override
    public void registerApplication(String host, Long port, String applicationUUID) {
        log.info("[registerApplication] registering application with uuid {}", applicationUUID);

        RegisterInfo registerInfo = registerMap.get(applicationUUID);
        if (registerInfo != null) {
            registerInfo.getSink().success(new CreateApplicationResponse(getIp(registerInfo.getPodName()), port));
        }
    }

    private String getIp(String podName) {
        try {
            V1Pod v1Pod = api.readNamespacedPod(podName, "default", null, null, null);
            String nodeName = v1Pod.getSpec().getNodeName();
            V1Node v1Node = api.readNode(nodeName, null, null, null);

            return v1Node.getStatus()
                    .getAddresses()
                    .stream()
                    .filter(a -> INTERNAL_IP.equals(a.getType()))
                    .findFirst()
                    .map(V1NodeAddress::getAddress)
                    .orElse(null);

        } catch (ApiException e) {
            log.info("[getIp] exception", e);
            return null;
        }
    }

    private V1Pod startApplication(ApplicationType type, String uuid) throws ApiException {
        return api.createNamespacedPod("default", PodHolder.getPod(type, uuid), null, null, null);
    }


    @Data
    @AllArgsConstructor
    private class RegisterInfo {
        private MonoSink<CreateApplicationResponse> sink;
        private String podName;
    }
}
