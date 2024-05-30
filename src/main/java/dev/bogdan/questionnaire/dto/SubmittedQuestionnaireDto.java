package dev.bogdan.questionnaire.dto;

import java.time.LocalDateTime;

public record SubmittedQuestionnaireDto(
        Long id,
        Integer version,
        Long userId,
        Long questionnaireId,
        LocalDateTime submittedAt
) {
}
