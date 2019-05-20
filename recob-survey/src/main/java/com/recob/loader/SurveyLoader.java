package com.recob.loader;

import com.recob.domain.holder.SurveyHolder;
import com.recob.domain.survey.Survey;
import com.recob.service.survey.ISurveyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@AllArgsConstructor
public class SurveyLoader implements CommandLineRunner {

    private Environment    environment;
    private ISurveyService surveyService;

    @Override
    public void run(String... args) {
        log.info("[run] finding survey");
        findSurvey();
        log.info("[run] survey setted");
    }

    private void findSurvey() {
//        String surveyId = environment.getProperty("surveyId");

        String surveyId = "5ce30c420f378e391c9710ca";
        if (StringUtils.isEmpty(surveyId)) {
            System.exit(0);
        }

        surveyService
                .findById(surveyId)
                .map(SurveyHolder::setSurvey)
                .subscribe();

    }
}
