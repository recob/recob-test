package com.recob.repository;

import com.recob.domain.survey.Survey;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SurveyRepository extends ReactiveMongoRepository<Survey, String> {

    Flux<Survey> findByTitleContains(String name);
}
