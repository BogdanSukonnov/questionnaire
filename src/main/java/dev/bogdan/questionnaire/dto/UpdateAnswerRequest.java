package dev.bogdan.questionnaire.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateAnswerRequest(
        @NotNull Long questionId,
        @NotNull Integer questionVersion,
        @NotNull Long id,
        @NotNull Integer version,
        String text,
        Integer checkedAnswer,
        Integer[] checkedAnswers
) {
}