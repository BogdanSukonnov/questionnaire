package dev.bogdan.questionnaire.dto;

import java.time.LocalDateTime;

public record QuestionnaireDto(
        Long id,
        Integer version,
        String title,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String description
) {
}
