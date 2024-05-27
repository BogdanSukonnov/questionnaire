package dev.bogdan.questionnaire.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NewQuestionnaireRequest(
        @NotNull String title,
        @NotNull LocalDateTime startDate,
        LocalDateTime endDate,
        @NotNull String description
) implements EndDateCheckable {
}
