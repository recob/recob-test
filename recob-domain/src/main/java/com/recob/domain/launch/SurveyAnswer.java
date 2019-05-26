package com.recob.domain.launch;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurveyAnswer {
    private SurveyUser user;
    private SurveyUserResponse response;
}

