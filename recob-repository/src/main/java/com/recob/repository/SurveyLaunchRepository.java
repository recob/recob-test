package com.recob.repository;

import com.recob.domain.launch.SurveyLaunch;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SurveyLaunchRepository extends ReactiveMongoRepository<SurveyLaunch, String> {

    Flux<SurveyLaunch> findBySurveyId(String surveyId);

}
