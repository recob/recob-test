package com.recob.domain.answer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * user answer
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "answer")
@IdClass(AnswerId.class)
public class Answer {
    private @NotNull @Id String         userId;
    private @NotNull @Id long           questionId;

    @ElementCollection(targetClass=String.class)
    private @NotNull     List<String>   answers;
}
