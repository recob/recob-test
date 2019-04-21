package com.recob.controller.schema;

import com.recob.controller.schema.dto.CreateSchemaModel;
import com.recob.domain.holder.TestSchemaHolder;
import com.recob.service.starter.ITestStarter;
import com.recob.service.test.ITestTransformer;
import io.netty.handler.codec.http.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * controller for uploading test schema
 * and starting test
 */

@RestController
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin("1*")
public class TestSchemaController {

    private ITestTransformer testTransformer;
    private ITestStarter     testStarter;
    private AtomicInteger    userCounter;

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
    public int startTest() {
        testStarter.startTest();
        return userCounter.get();
    }
}
