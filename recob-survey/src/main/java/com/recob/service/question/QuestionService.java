package com.recob.service.question;

import com.recob.controller.ws.answer.dto.AnswerMessage;
import com.recob.domain.answer.UserAnswer;
import com.recob.domain.holder.SurveyHolder;
import com.recob.domain.survey.Question;
import com.recob.domain.survey.Survey;
import com.recob.map.AnswerRepository;
import com.recob.service.question.dto.NextQuestionResponse;
import com.recob.service.statistic.IStatisticService;
import com.recob.service.transform.ITransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Slf4j
@AllArgsConstructor
@Service
public class QuestionService implements IQuestionService {

    private final DirectProcessor<NextQuestionResponse> stream = DirectProcessor.create();

    private AnswerRepository       answerRepository;
    private IStatisticService      statisticService;

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

    private Mono<NextQuestionResponse> getStartedQuestions(UserAnswer userAnswer) {
        if (userAnswer != null) {

            if (SurveyHolder.hasSurveyStarted()) {
                long maxQuestionId = userAnswer.getAnswerMap().keySet().stream().mapToLong(l -> l).max().orElse(-1);

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
