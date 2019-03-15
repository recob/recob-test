package com.recob.service.answer;

import com.recob.controller.ws.dto.AnswerMessage;
import com.recob.domain.answer.Answer;
import com.recob.service.question.NextQuestionResponse;
import com.recob.domain.question.Option;
import com.recob.domain.question.Question;
import com.recob.repository.AnswerRepository;
import com.recob.service.question.IQuestionService;
import com.recob.service.question.QuestionOptionResponse;
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
public class AnswerService implements IAnswerService {

    private final DirectProcessor<NextQuestionResponse> stream = DirectProcessor.create();

    private IQuestionService questionService;
    private AnswerRepository answerRepository;

    @Override
    public Mono<Void> saveAnswer(Flux<AnswerMessage> inbound) {
        return inbound
                .onBackpressureBuffer()
                .flatMap(this::transformAnswer)
                .map(answerRepository::save)
                .map(answer -> questionService.getNextQuestion(answer.getQuestionId()))
                .map(this::transformMessage)
                .doOnNext(stream.sink()::next)
                .then();
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

    @Override
    public Flux<NextQuestionResponse> stream() {
        return stream;
    }

    private NextQuestionResponse transformMessage(Question question) {
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
