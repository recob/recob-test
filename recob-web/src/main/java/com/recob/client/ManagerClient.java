package com.recob.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "foo", url ="${MANAGER_SERVICE_HOST}" )
public interface ManagerClient {

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    Map<String, Object> create(Map<String, Object> body);
}
