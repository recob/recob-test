package com.recob.service.result;

import com.recob.domain.answer.UserAnswer;
import com.recob.domain.holder.SurveyHolder;
import com.recob.domain.launch.*;
import com.recob.domain.survey.Question;
import com.recob.domain.survey.QuestionOption;
import com.recob.domain.survey.QuestionType;
import com.recob.domain.survey.Survey;
import com.recob.domain.user.RecobUser;
import com.recob.map.AnswerRepository;
import com.recob.repository.SurveyLaunchRepository;
import com.recob.service.user.IRecobUserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SurveyResultService implements ISurveyResultService {

    private final Map<String, DirectProcessor<Object>> streamMap = new HashMap<>();

    private IRecobUserService      recobUserService;
    private AnswerRepository       answerRepository;
    private SurveyLaunchRepository launchRepository;


    @Override
    public Mono<SurveyLaunch> validateAnswers() {
        Survey survey = SurveyHolder.getSurvey();

        Map<String, RecobUser> userMap = recobUserService.findAll().stream().collect(Collectors.toMap(RecobUser::getId, Function.identity()));
        Map<SurveyUser, SurveyUserResponse> answerMap = new HashMap<>();
        answerRepository.findAll().forEach(a -> validateQuestions(a, survey, userMap, answerMap));

        answerMap.forEach((u, a) -> {
            DirectProcessor<Object> stream = streamMap.get(u.getId());
            if (stream != null) {
                stream.sink().next(a);
            }
        });

        SurveyLaunch launch = new SurveyLaunch();
        launch.setDate(new Date());
        launch.setSurveyId(survey.getId());
        launch.setResponses(transformAnswerMap(answerMap));

        return launchRepository.save(launch);
    }



    @Override
    public Flux<?> stream() {

        return currentUser().map(u -> {
            DirectProcessor<Object> stream = DirectProcessor.create();
            streamMap.put(u, stream);
            return stream;
        }).flatMapMany(Function.identity());

    }

    private void validateQuestions(UserAnswer userAnswer, Survey survey, Map<String, RecobUser> userMap, Map<SurveyUser, SurveyUserResponse> responseMap) {

        SurveyUserResponse response = new SurveyUserResponse();
        response.setTitle(survey.getTitle());
        response.setThumbnail(survey.getThumbnail());

        List<UserQuestionModel> questions = new ArrayList<>();

        for (Question q : survey.getQuestions().values()) {
            UserQuestionModel model = new UserQuestionModel();
            model.setId(String.valueOf(q.getPosition()));
            model.setTitle(q.getTitle());
            model.setType(q.getType());

            List<UserQuestionOption> options = new ArrayList<>();

            if (q.getOptions() != null) {

                for (QuestionOption o : q.getOptions()) {
                    UserQuestionOption option = new UserQuestionOption();
                    option.setId(o.getId());
                    option.setRight(o.isRight());
                    option.setValue(o.getValue());

                    if (QuestionType.OPEN.equals(model.getType())) {
                        option.setUserAnswer(userAnswer.getAnswerMap().get(q.getPosition()).get(0).trim().equalsIgnoreCase(o.getValue().toString()));
                    } else {
                        List<String> answers = userAnswer.getAnswerMap().get(q.getPosition());
                        if (answers != null) {
                            option.setUserAnswer(answers.contains(option.getId()));
                        }
                    }

                    options.add(option);
                }

            }

            model.setOptions(options);
            questions.add(model);

        }

        response.setQuestions(questions);

        RecobUser user = userMap.get(userAnswer.getUserId());
        responseMap.put(new SurveyUser(user.getId(), user.getName()), response);
    }

    private List<SurveyAnswer> transformAnswerMap(Map<SurveyUser, SurveyUserResponse> answerMap) {
        List<SurveyAnswer> result = new ArrayList<>();

        if (!CollectionUtils.isEmpty(answerMap)) {
            answerMap.forEach((key, value) -> {
                result.add(new SurveyAnswer(key, value));
            });
        }

        return result;
    }

    private static Mono<String> currentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName);
    }


}
