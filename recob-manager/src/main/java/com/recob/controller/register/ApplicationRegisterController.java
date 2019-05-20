package com.recob.controller.register;

import com.recob.controller.register.dto.RegisterApplicationRequest;
import com.recob.service.IApplicationManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class ApplicationRegisterController {

    private IApplicationManager applicationManager;

    @PostMapping("/register")
    public void registerApplication(@RequestBody RegisterApplicationRequest request) {
        applicationManager.registerApplication(request.getHost(), request.getPort(), request.getUuid());
    }
}
