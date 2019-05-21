package com.recob.service.question;

import com.recob.controller.ws.answer.dto.AnswerMessage;
import com.recob.domain.answer.UserAnswer;
import com.recob.domain.holder.SurveyHolder;
import com.recob.domain.launch.SurveyLaunch;
import com.recob.domain.launch.SurveyUser;
import com.recob.domain.launch.ValidatedAnswer;
import com.recob.domain.survey.Question;
import com.recob.domain.survey.QuestionOption;
import com.recob.domain.survey.QuestionType;
import com.recob.domain.survey.Survey;
import com.recob.domain.user.RecobUser;
import com.recob.map.AnswerRepository;
import com.recob.repository.SurveyLaunchRepository;
import com.recob.service.question.dto.NextQuestionResponse;
import com.recob.service.statistic.IStatisticService;
import com.recob.service.transform.ITransformer;
import com.recob.service.user.IRecobUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class QuestionService implements IQuestionService {

    private final DirectProcessor<NextQuestionResponse> stream = DirectProcessor.create();

    private AnswerRepository       answerRepository;
    private IStatisticService      statisticService;
    private IRecobUserService      recobUserService;
    private SurveyLaunchRepository launchRepository;

    private ITransformer<NextQuestionResponse, Question> questionTransformer;

    @Override
    public Mono<Void> getNextQuestion(Flux<AnswerMessage> inbound) {
        return inbound
                .flatMap(this::saveAnswer)
                .doOnNext(statisticService::registerAnswer)
                .map(this::getNextQuestionResponse)
                .doOnNext(stream.sink()::next)
                .then();
    }


    @Override
    public Flux<NextQuestionResponse> stream() {
        return stream;
    }

    @Override
    public Mono<NextQuestionResponse> getStartedQuestions() {

        return currentUser()
                .flatMap((user) -> getStartedQuestions(answerRepository.findById(user).orElse(null)));
    }

    @Override
    public Mono<SurveyLaunch> validateQuestions() {

        Survey survey = SurveyHolder.getSurvey();
        Map<String, RecobUser> userMap = recobUserService.findAll().stream().collect(Collectors.toMap(RecobUser::getId, Function.identity()));

        Map<SurveyUser, List<ValidatedAnswer>> answerMap = new HashMap<>();
        answerRepository.findAll().forEach(a -> validateQuestions(a, survey, userMap, answerMap));

        SurveyLaunch launch = new SurveyLaunch();
        launch.setDate(new Date());
        launch.setSurveyId(survey.getId());
        launch.setAnswers(answerMap);

        return launchRepository.save(launch);
    }

    private void validateQuestions(UserAnswer userAnswer, Survey survey, Map<String, RecobUser> userMap, Map<SurveyUser, List<ValidatedAnswer>> answerMap) {

        RecobUser user = userMap.get(userAnswer.getUserId());
        SurveyUser surveyUser = new SurveyUser(user.getId(), user.getName());

        List<ValidatedAnswer> validatedAnswers = new ArrayList<>();

        userAnswer.getAnswerMap().forEach((k, v) -> {
            Question question = survey.getQuestions().get(k);

            boolean isRight;

            if (QuestionType.OPEN.equals(question.getType())) {
                isRight = v.get(0).equalsIgnoreCase(String.valueOf(question.getOptions().get(0).getValue()));
            } else {
                List<String> rightAnswers = question.getOptions()
                        .stream()
                        .filter(QuestionOption::isRight)
                        .map(QuestionOption::getId)
                        .collect(Collectors.toList());

                isRight = rightAnswers.equals(v);
            }

            ValidatedAnswer validatedAnswer = new ValidatedAnswer();
            validatedAnswer.setAnswers(v);
            validatedAnswer.setQuestionId(k);
            validatedAnswer.setRight(isRight);

            validatedAnswers.add(validatedAnswer);
        });

        answerMap.put(surveyUser, validatedAnswers);

    }

    private Mono<NextQuestionResponse> getStartedQuestions(UserAnswer userAnswer) {
        if (userAnswer != null) {

            long maxQuestionId = userAnswer.getAnswerMap().keySet().stream().mapToLong(l -> l).max().orElse(-1);

            if (maxQuestionId >= 0) {
                Question nextQuestion = getNextQuestion(maxQuestionId + 1);
                return Mono.just(questionTransformer.transform(nextQuestion));
            }
        }

        return Mono.never();
    }

    private NextQuestionResponse getNextQuestionResponse(long nextQuestion) {
        Question question = getNextQuestion(nextQuestion);

        if (question != null) {
            return questionTransformer.transform(question);
        }

        NextQuestionResponse lastMessage = new NextQuestionResponse();
        lastMessage.setDone(true);

        return lastMessage;

    }

    private Question getNextQuestion(long nextQuestion) {
        Survey survey = SurveyHolder.getSurvey();
        nextQuestion += 1;

        if (survey.getQuestions().containsKey(nextQuestion)) {
            return survey.getQuestions().get(nextQuestion);
        }

        return null;
    }

    private Mono<Long> saveAnswer(AnswerMessage answerMessage) {
        return currentUser()
                .map((user) -> saveAnswer(answerMessage, user));
    }

    private Long saveAnswer(AnswerMessage answerMessage, String user) {
        return answerRepository.findById(user)
                .map(a -> {
                    a.getAnswerMap().put(answerMessage.getId(), answerMessage.getAnswer());
                    answerRepository.save(a);
                    return answerMessage.getId();
                }).orElse(0L);
    }

    private static Mono<String> currentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName);
    }
}
