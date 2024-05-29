package dev.bogdan.questionnaire.dto;

import dev.bogdan.questionnaire.model.QuestionType;
import jakarta.validation.constraints.NotNull;

public record UpdateQuestionRequest(
        @NotNull Long id,
        Integer version,
        @NotNull Long questionnaireId,
        @NotNull Integer orderNumber,
        @NotNull String text,
        @NotNull QuestionType type,
        String[] answers
) {
}