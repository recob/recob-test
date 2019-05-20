package com.recob.service.transformer;

import com.recob.domain.survey.QuestionOption;
import com.recob.service.question.dto.QuestionOptionResponse;
import com.recob.service.transform.ITransformer;
import org.springframework.stereotype.Component;

@Component
public class QuestionOptionTransformer implements ITransformer<QuestionOptionResponse, QuestionOption> {

    @Override
    public QuestionOptionResponse transform(QuestionOption questionOption) {
        QuestionOptionResponse response = new QuestionOptionResponse();

        response.setValue(questionOption.getValue());
        response.setId(questionOption.getId());

        return response;
    }
}
