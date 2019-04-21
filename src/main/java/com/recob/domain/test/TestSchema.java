package com.recob.domain.test;

import com.recob.domain.question.Question;
import lombok.Data;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Class contains information about test
 *
 * {@link TestSchema#availableTime} time for test seconds
 * {@link TestSchema#questions} test questions
 */

@Data
public class TestSchema {
    private @Nullable long                availableTime;
    private @NotNull  Map<Long, Question> questions;
}
