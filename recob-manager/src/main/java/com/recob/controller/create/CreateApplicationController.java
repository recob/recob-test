package com.recob.controller.create;

import com.recob.controller.create.dto.CreateApplicationRequest;
import com.recob.service.CreateApplicationResponse;
import com.recob.service.IApplicationManager;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@CrossOrigin
public class CreateApplicationController {

    private IApplicationManager applicationManager;

    @PostMapping("/create")
    public Mono<CreateApplicationResponse> createApplication(@RequestBody Mono<CreateApplicationRequest> request) {
        return request.flatMap(r -> applicationManager.createApplication(r.getSurveyId()));
    }
}
