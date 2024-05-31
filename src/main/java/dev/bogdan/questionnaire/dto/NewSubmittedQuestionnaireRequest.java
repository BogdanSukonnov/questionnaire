package dev.bogdan.questionnaire.dto;

import jakarta.validation.constraints.NotNull;

public record NewSubmittedQuestionnaireRequest(
        @NotNull Long userId,
        @NotNull Long questionnaireId,
        @NotNull Integer questionnaireVersion
) {
}
