package com.recob.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * statistic for main screen
 * {@code id} is question id
 */
@Data
@AllArgsConstructor
public class AnswerStatistic {
    private long connected;
    private long finished;
}
