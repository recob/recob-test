package com.recob.service.answer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnswerService implements IAnswerService {

    @Override
    public void saveAnswer(long questionId, String answer, String userId) {
        // TODO implement logic
        log.info("[saveAnswer] questionId {}, answer {}, userId {}", questionId, answer, userId);
    }
}
