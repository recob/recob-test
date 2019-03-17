package com.recob.loader;

import com.recob.domain.holder.TestSchemaHolder;
import com.recob.domain.question.Option;
import com.recob.domain.question.Question;
import com.recob.domain.question.QuestionType;
import com.recob.domain.test.TestSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * load test schema if profile is dev
 */

@Component
@Slf4j
@Profile("dev")
public class TestSchemaLoader implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("[run] creating schema");
        TestSchema testSchema = createTestSchema();
        log.info("[run] setting schema");
        TestSchemaHolder.setTestSchema(testSchema);
    }

    private TestSchema createTestSchema() {
        TestSchema testSchema = new TestSchema();
        testSchema.setQuestions(new HashMap<>());

        for (int i = 0; i < 5; i++) {
            Question question = new Question();
            question.setPosition(i);
            question.setQuestionType(QuestionType.MULTIPLE);
            question.setValue(UUID.randomUUID().toString());

            question.setOptions(new ArrayList<>());

            for (int j = 0; j < 4; j++) {
                Option option = new Option();
                option.setRight(true);
                option.setValue(UUID.randomUUID().toString());
                option.setId(UUID.randomUUID().toString());

                question.getOptions().add(option);
            }

            testSchema.getQuestions().put(question.getPosition(), question);
        }

        return testSchema;
    }
}
