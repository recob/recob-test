package com.recob.domain.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * user answer
 */
@Data
@AllArgsConstructor
public class UserAnswer {
    private @NotNull @Id String                  userId;
    private @NotNull     Map<Long, List<String>> answerMap;
}
