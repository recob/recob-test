package com.recob.service.test;

import com.recob.controller.schema.dto.CreateSchemaModel;
import com.recob.domain.question.Question;
import com.recob.domain.test.TestSchema;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TestTransformer implements ITestTransformer {

    @Override
    public Mono<TestSchema> transformTestModel(Mono<CreateSchemaModel> model) {
        return model.map(this::convertModel);
    }

    private TestSchema convertModel(CreateSchemaModel m) {
        TestSchema testSchema = new TestSchema();

        testSchema.setAvailableTime(m.getAvailableTime());
        // key - question number, value - question
        Map<Long, Question> questionMap = m.getQuestions()
                .stream()
                .collect(Collectors.toMap(Question::getPosition, Function.identity()));

        testSchema.setQuestions(questionMap);

        return testSchema;
    }
}
