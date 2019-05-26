package com.recob.service.starter;

import com.recob.domain.holder.SurveyHolder;
import com.recob.domain.survey.Survey;
import com.recob.service.question.dto.NextQuestionResponse;
import com.recob.service.transformer.QuestionTransformer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SurveyManager implements ISurveyManager {

    private final DirectProcessor<NextQuestionResponse> stream = DirectProcessor.create();

    private QuestionTransformer questionTransformer;

    @Override
    public void startSurvey() {
        stream.sink().next(getFirstQuestion());
    }

    @Override
    public void stopSurvey() {
//        stream.sink().next(Mono.empty());
    }

    @Override
    public Flux<NextQuestionResponse> stream() {
        return stream;
    }

    private NextQuestionResponse getFirstQuestion() {
        Survey survey = SurveyHolder.getSurvey();

        return questionTransformer.transform(survey.getQuestions().get(0L));
    }
}
