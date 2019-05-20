package com.recob.service.transformer;

import com.recob.domain.survey.Question;
import com.recob.service.question.dto.NextQuestionResponse;
import com.recob.service.transform.ITransformer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuestionTransformer implements ITransformer<NextQuestionResponse, Question> {

    private QuestionOptionTransformer optionTransformer;

    @Override
    public NextQuestionResponse transform(Question question) {
        NextQuestionResponse nextQuestionResponse = new NextQuestionResponse();

        nextQuestionResponse.setTitle(question.getTitle());
        nextQuestionResponse.setId(String.valueOf(question.getPosition()));
        nextQuestionResponse.setType(question.getType());
        nextQuestionResponse.setOptions(optionTransformer.transformList(question.getOptions()));

        return nextQuestionResponse;
    }
}
