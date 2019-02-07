package com.recob.service.test;

import com.recob.controller.schema.dto.request.CreateSchemaModel;
import com.recob.domain.test.TestSchema;
import reactor.core.publisher.Mono;

/**
 * Convert test model into test
 */

public interface ITestTransformer {
    /**
     * convert test model into test
     * test model should be already validated
     * @param model model
     * @return test
     */
    Mono<TestSchema> transformTestModel(Mono<CreateSchemaModel> model);
}
