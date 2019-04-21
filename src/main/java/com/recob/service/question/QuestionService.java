package com.recob.service.question;

import com.recob.controller.ws.answer.dto.AnswerMessage;
import com.recob.domain.holder.TestSchemaHolder;
import com.recob.domain.question.Question;
import com.recob.domain.test.TestSchema;
import com.recob.repository.AnswerRepository;
import com.recob.service.question.dto.NextQuestionResponse;
import com.recob.service.statistic.IStatisticService;
import com.recob.service.transformer.QuestionTransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionService implements IQuestionService {

    private final DirectProcessor<NextQuestionResponse> stream = DirectProcessor.create();

    private AnswerRepository    answerRepository;
    private QuestionTransformer questionTransformer;
    private IStatisticService statisticService;

    @Override
    public Mono<Void> getNextQuestion(Flux<AnswerMessage> inbound) {
        return inbound
                .onBackpressureBuffer()
                .flatMap(this::saveAnswer)
                .doOnNext(statisticService::registerAnswer)
                .map(this::getNextQuestion)
                .map(questionTransformer::transform)
                .doOnNext(stream.sink()::next)
                .then();
    }

    @Override
    public Flux<NextQuestionResponse> stream() {
        return stream;
    }

    private Question getNextQuestion(long nextQuestion) {
        TestSchema testSchema = TestSchemaHolder.getTestSchema();

        if (testSchema != null) {
            return testSchema.getQuestions().get(nextQuestion);
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
                    return answerMessage.getId() + 1;
                }).orElse(0L);
    }

    private static Mono<String> currentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName);
    }
}
