package com.recob.service.starter;

import com.recob.service.question.IQuestionService;
import com.recob.service.question.dto.NextQuestionResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TestStarter implements ITestStarter {

    private final DirectProcessor<NextQuestionResponse> stream = DirectProcessor.create();

    private IQuestionService questionService;

    @Override
    public void startTest() {
        stream.sink().next(questionService.getFirstQuestion());
    }

    @Override
    public Flux<NextQuestionResponse> stream() {
        return stream;
    }
}
