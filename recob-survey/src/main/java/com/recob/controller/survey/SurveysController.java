package com.recob.controller.survey;

import com.recob.domain.holder.SurveyHolder;
import com.recob.domain.launch.SurveyLaunch;
import com.recob.domain.model.SurveyResponse;
import com.recob.domain.survey.Survey;
import com.recob.domain.user.RecobUser;
import com.recob.service.result.ISurveyResultService;
import com.recob.service.starter.ISurveyManager;
import com.recob.service.transform.ITransformer;
import com.recob.service.user.IRecobUserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * controller for uploading test survey
 * and starting test
 */

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin
public class SurveysController {

    private ISurveyManager       surveyManager;
    private IRecobUserService    recobUserService;
    private ISurveyResultService surveyResultService;
    private ITransformer<SurveyResponse, Survey> surveyTransformer;

    /**
     * start current test
     * will push first question to users
     * @return userList
     */
    @PostMapping("/survey/start")
    public StartSurveyResponse startSurvey() {

        SurveyHolder.startSurvey();

        surveyManager.startSurvey();

        List<RecobUser> users = recobUserService.findAll();
        Survey survey = SurveyHolder.getSurvey();

        return new StartSurveyResponse(users, surveyTransformer.transform(survey));
    }

    @PostMapping("/survey/stop")
    public Mono<SurveyLaunch> stopSurvey() {
        surveyManager.stopSurvey();
        return surveyResultService.validateAnswers();
    }

    @Data
    @AllArgsConstructor
    private class StartSurveyResponse {
        private List<RecobUser> users;
        private SurveyResponse survey;
    }
}
