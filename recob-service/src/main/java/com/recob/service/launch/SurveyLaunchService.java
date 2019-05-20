package com.recob.service.launch;

import com.recob.domain.launch.SurveyLaunch;
import com.recob.repository.SurveyLaunchRepository;
import com.recob.service.AbstractService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class SurveyLaunchService extends AbstractService<SurveyLaunch, String, SurveyLaunchRepository> implements ISurveyLaunchService {

    public SurveyLaunchService(SurveyLaunchRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<SurveyLaunch> findBySurveyId(String surveyId) {
        return repository.findBySurveyId(surveyId);
    }
}
