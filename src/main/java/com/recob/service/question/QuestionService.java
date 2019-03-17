package com.recob.service.question;

import com.recob.controller.ws.dto.AnswerMessage;
import com.recob.domain.answer.Answer;
import com.recob.domain.holder.TestSchemaHolder;
import com.recob.domain.question.Option;
import com.recob.domain.question.Question;
import com.recob.domain.test.TestSchema;
import com.recob.repository.AnswerRepository;
import com.recob.service.question.dto.NextQuestionResponse;
import com.recob.service.question.dto.QuestionOptionResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionService implements IQuestionService {

    private final DirectProcessor<NextQuestionResponse> stream = DirectProcessor.create();

    private AnswerRepository answerRepository;

    @Override
    public Mono<Void> getNextQuestion(Flux<AnswerMessage> inbound) {
        return inbound
                .onBackpressureBuffer()
                .flatMap(this::transformAnswer)
                .map(answerRepository::save)
                .map(answer -> getNextQuestion(answer.getQuestionId()))
                .map(this::transformQuestion)
                .doOnNext(stream.sink()::next)
                .then();
    }

    @Override
    public Flux<NextQuestionResponse> stream() {
        return stream;
    }

    @Override
    public NextQuestionResponse getFirstQuestion() {
        TestSchema testSchema = TestSchemaHolder.getTestSchema();

        if (testSchema != null) {
            return transformQuestion(testSchema.getQuestions().get(0L));
        }

        return null;
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
                            answer.setAnswerId(answerMessage.getAnswerId());
                            answer.setQuestionId(answerMessage.getQuestionId());

                            return answer;
                        }
                );
    }


    private NextQuestionResponse transformQuestion(Question question) {
        NextQuestionResponse nextQuestionResponse = new NextQuestionResponse();

        nextQuestionResponse.setValue(question.getValue());
        nextQuestionResponse.setPosition(question.getPosition());
        nextQuestionResponse.setType(question.getQuestionType());
        nextQuestionResponse.setOptions(transformOptions(question.getOptions()));

        return nextQuestionResponse;
    }

    private List<QuestionOptionResponse> transformOptions(List<Option> options) {
        if (!CollectionUtils.isEmpty(options)) {
            return options.stream()
                    .map(this::transformOption)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private QuestionOptionResponse transformOption(Option option) {
        QuestionOptionResponse response = new QuestionOptionResponse();

        response.setValue(option.getValue());
        response.setId(option.getId());

        return response;
    }


    private static Mono<String> currentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName);
    }
}
