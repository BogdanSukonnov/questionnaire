package dev.bogdan.questionnaire.dto;

import java.time.LocalDateTime;

public record NewSubmittedQuestionnaireRequest(
        Long userId,
        Long questionnaireId,
        Integer questionnaireVersion,
        LocalDateTime submittedAt
) {
}
