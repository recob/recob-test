package com.recob.service.question;

import com.recob.domain.holder.TestSchemaHolder;
import com.recob.domain.question.Question;
import com.recob.domain.test.TestSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class QuestionService implements IQuestionService {

    @Override
    public Question getNextQuestion(long currentQuestion) {
        TestSchema testSchema = TestSchemaHolder.getTestSchema();

        if (testSchema != null) {
            long nextQuestion = currentQuestion + 1;

            return testSchema.getQuestions().get(nextQuestion);
        }
        return null;
    }

    @Override
    public Mono<Question> getFirstQuestion() {
        TestSchema testSchema = TestSchemaHolder.getTestSchema();

        if (testSchema != null) {
            return Mono.just(testSchema.getQuestions().get(0L));
        }

        return Mono.empty();
    }
}
