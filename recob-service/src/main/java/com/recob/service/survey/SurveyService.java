package com.recob.service.survey;

import com.recob.domain.survey.Survey;
import com.recob.repository.SurveyRepository;
import com.recob.service.AbstractService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class SurveyService extends AbstractService<Survey, String, SurveyRepository> implements ISurveyService {

    public SurveyService(SurveyRepository surveyRepository) {
        this.repository = surveyRepository;
    }

    @Override
    public Flux<Survey> findByTitle(String name) {
        return repository.findByTitleContains(name);
    }
}
