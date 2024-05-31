package dev.bogdan.questionnaire.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateQuestionnaireRequest(
        @NotNull Long id,
        @NotNull Integer version,
        @NotNull String title,
        @NotNull LocalDateTime startDate,
        LocalDateTime endDate,
        @NotNull String description
) implements EndDateCheckable {
}
