package com.recob.holder;

import com.recob.domain.ApplicationType;
import io.kubernetes.client.models.*;

import java.util.*;
import java.util.function.Function;

public class PodHolder {

    private static final Map<ApplicationType, Function<String, V1Pod>> podMap = new HashMap<ApplicationType, Function<String, V1Pod>>() {{
        put(ApplicationType.test, PodHolder::getTestPod);
    }};

    public static V1Pod getPod(ApplicationType type, String uuid) {
        return podMap.get(type).apply(uuid);
    }

    private static V1Pod getTestPod(String uuid) {
        V1Pod pod = new V1Pod();

        pod.setApiVersion("v1");
        pod.setKind("Pod");
        pod.setMetadata(getTestMetadata());
        pod.setSpec(getTestSpec(uuid));

        return pod;
    }

    private static V1PodSpec getTestSpec(String uuid) {
        V1PodSpec spec = new V1PodSpec();

        spec.setHostNetwork(true);
        spec.setRestartPolicy("Never");
        spec.setContainers(getTestContainers(uuid));

        return spec;
    }

    private static List<V1Container> getTestContainers(String uuid) {
        V1Container container = new V1Container();

        container.setName("recob-test");
        container.setImage("recob/recob-test");
        container.setImagePullPolicy("Never");
        container.setEnv(getTestEnv(uuid));

        return Collections.singletonList(container);
    }

    private static List<V1EnvVar> getTestEnv(String uuid) {
        List<V1EnvVar> list = new ArrayList<>();

        list.add(getApplicationUUID(uuid));

        return list;
    }

    private static V1EnvVar getApplicationUUID(String uuid) {
        V1EnvVar var = new V1EnvVar();

        var.setName("RECOB_UUID");
        var.setValue(uuid);

        return var;
    }

   private static V1ObjectMeta getTestMetadata() {
        V1ObjectMeta meta = new V1ObjectMeta();

        meta.setGenerateName("recob-test-");

        return meta;
    }
}

