package com.recob.controller.schema;

import com.recob.controller.schema.dto.CreateSchemaModel;
import com.recob.domain.holder.TestSchemaHolder;
import com.recob.service.starter.ITestStarter;
import com.recob.service.test.ITestTransformer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;
import java.security.Principal;

/**
 * controller for uploading test schema
 * and starting test
 */

@RestController
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TestSchemaController {

    private ITestTransformer testTransformer;
    private ITestStarter     testStarter;

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

    /**
     * start current test
     * will push first question to users
     */
    @PostMapping("/start")
    public void startTest() {
        testStarter.startTest();
    }
}
