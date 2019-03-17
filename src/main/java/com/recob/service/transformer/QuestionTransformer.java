package com.recob.service.transformer;

import com.recob.domain.question.Question;
import com.recob.service.question.dto.NextQuestionResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionTransformer implements ITransformer <NextQuestionResponse, Question> {

    private QuestionOptionTransformer optionTransformer;

    @Override
    public NextQuestionResponse transform(Question question) {
        NextQuestionResponse nextQuestionResponse = new NextQuestionResponse();

        nextQuestionResponse.setValue(question.getValue());
        nextQuestionResponse.setQuestionId(question.getPosition());
        nextQuestionResponse.setType(question.getQuestionType());
        nextQuestionResponse.setOptions(optionTransformer.transformList(question.getQuestionOptions()));

        return nextQuestionResponse;
    }
}
