package com.recob.controller.schema.dto;

import com.recob.domain.question.Question;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Schema for creating test
 */

@Data
public class CreateSchemaModel {
    private @Nullable long           availableTime;
    private @NotNull  List<Question> questions;
}
