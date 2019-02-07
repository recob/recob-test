package com.recob.controller.schema;

import com.recob.controller.schema.dto.request.CreateSchemaModel;
import com.recob.domain.holder.TestSchemaHolder;
import com.recob.service.test.ITestTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;
import java.security.Principal;

/**
 * controller for uploading test schema
 */

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestSchemaController {

    private final ITestTransformer testTransformer;

    /**
     * upload test schema as file
     * @param request test model
     * @return empty mono atm
     */
    @PostMapping("/schema")
    public Mono<Object>
    createSchema(@RequestBody @Valid Mono<CreateSchemaModel> request, Principal principal) {
        return request
                .log()
                .publishOn(Schedulers.parallel())
                .transform(testTransformer::transformTestModel)
                .map(TestSchemaHolder::setTestSchema);
    }

    @GetMapping("/")
    public Mono<String>
    helloWorld(Principal principal) {
        return Mono.just(principal.getName());
    }
}
