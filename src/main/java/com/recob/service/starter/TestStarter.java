package com.recob.service.starter;

import com.recob.domain.holder.TestSchemaHolder;
import com.recob.domain.test.TestSchema;
import com.recob.service.question.dto.NextQuestionResponse;
import com.recob.service.transformer.QuestionTransformer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TestStarter implements ITestStarter {

    private final DirectProcessor<NextQuestionResponse> stream = DirectProcessor.create();

    private QuestionTransformer questionTransformer;

    @Override
    public void startTest() {
        stream.sink().next(getFirstQuestion());
    }

    @Override
    public Flux<NextQuestionResponse> stream() {
        return stream;
    }

    private NextQuestionResponse getFirstQuestion() {
        TestSchema testSchema = TestSchemaHolder.getTestSchema();

        if (testSchema != null) {
            return questionTransformer.transform(testSchema.getQuestions().get(0L));
        }

        return null;
    }
}
