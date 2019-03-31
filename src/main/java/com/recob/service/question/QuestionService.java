package com.recob.service.question;

import com.recob.controller.ws.dto.AnswerMessage;
import com.recob.domain.answer.Answer;
import com.recob.domain.holder.TestSchemaHolder;
import com.recob.domain.question.Question;
import com.recob.domain.test.TestSchema;
import com.recob.repository.AnswerRepository;
import com.recob.service.question.dto.NextQuestionResponse;
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

    @Override
    public Mono<Void> getNextQuestion(Flux<AnswerMessage> inbound) {
        return inbound
                .onBackpressureBuffer()
                .flatMap(this::transformAnswer)
                .map(answerRepository::save)
                .map(answer -> getNextQuestion(answer.getQuestionId()))
                .map(questionTransformer::transform)
                .doOnNext(stream.sink()::next)
                .then();
    }

    @Override
    public Flux<NextQuestionResponse> stream() {
        return stream;
    }

    private Question getNextQuestion(long currentQuestion) {
        TestSchema testSchema = TestSchemaHolder.getTestSchema();

        if (testSchema != null) {
            long nextQuestion = currentQuestion + 1;

            return testSchema.getQuestions().get(nextQuestion);
        }
        return null;
    }

    private Mono<Answer> transformAnswer(AnswerMessage answerMessage) {
        return currentUser()
                .map((user) -> {
                            Answer answer = new Answer();

                            answer.setUserId(user);
                            answer.setAnswers(answerMessage.getAnswer());
                            answer.setQuestionId(answerMessage.getId());

                            return answer;
                        }
                );
    }

    private static Mono<String> currentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName);
    }
}
