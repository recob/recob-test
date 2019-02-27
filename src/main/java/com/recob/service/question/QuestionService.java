package com.recob.service.question;

import com.recob.domain.holder.TestSchemaHolder;
import com.recob.domain.question.Question;
import com.recob.domain.test.TestSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuestionService implements IQuestionService {

    @Override
    public Question getNextQuestion(long currentQuestion) {
        TestSchema testSchema = TestSchemaHolder.getTestSchema();
        if (testSchema != null) {
            long nextQuestion = currentQuestion + 1;

            if (testSchema.getQuestions().containsKey(nextQuestion)) {
                return testSchema.getQuestions().get(nextQuestion);
            }

            return null;
        }
        return null;
    }
}
