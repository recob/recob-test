package com.recob.domain.survey;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Class contains information about test
 *
 * {@link Survey#availableTime} time for test seconds
 * {@link Survey#questions} test questions
 */
@Data
@Document("survey")
public class Survey {
    private @Id       String              id;
    private @Nullable long                availableTime;
    private @NotNull  Map<Long, Question> questions;
    private @Nullable String              thumbnail;

    private @NotNull  @Indexed String     title;
}
