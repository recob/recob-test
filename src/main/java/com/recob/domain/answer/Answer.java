package com.recob.domain.answer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * user answer
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "answer")
@IdClass(AnswerId.class)
public class Answer {
    @Id
    private @NotNull String userId;
    @Id
    private @NotNull long   questionId;

    private @NotNull String answerId;
}
