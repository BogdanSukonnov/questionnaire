package dev.bogdan.questionnaire.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateQuestionsOrderNumbersRequestItem(

        @NotNull Long questionId,

        Integer version,

        @NotNull Integer orderNumber

) {
}