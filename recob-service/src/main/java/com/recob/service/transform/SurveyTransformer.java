package com.recob.service.transform;

import com.recob.domain.model.QuestionModel;
import com.recob.domain.model.SurveyResponse;
import com.recob.domain.survey.Question;
import com.recob.domain.survey.Survey;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SurveyTransformer implements ITransformer<SurveyResponse, Survey> {

    @Override
    public SurveyResponse transform(Survey survey) {
        SurveyResponse surveyResponse = new SurveyResponse();

        surveyResponse.setId(survey.getId());
        surveyResponse.setAvailableTime(survey.getAvailableTime());
        surveyResponse.setTitle(survey.getTitle());
        surveyResponse.setThumbnail(survey.getThumbnail());

        surveyResponse.setQuestions(survey.getQuestions()
                .values()
                .stream()
                .map(this::transformQuestion)
                .collect(Collectors.toList()));

        return surveyResponse;
    }

    private QuestionModel transformQuestion(Question question) {
        QuestionModel questionModel = new QuestionModel();

        questionModel.setTitle(question.getTitle());
        questionModel.setOptions(question.getOptions());
        questionModel.setId(String.valueOf(question.getPosition()));
        questionModel.setType(question.getType());

        return questionModel;
    }
}
