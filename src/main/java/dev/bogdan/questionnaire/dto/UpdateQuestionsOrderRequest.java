package dev.bogdan.questionnaire.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UpdateQuestionsOrderRequest(

        @NotNull Long questionnaireId,

        @NotNull Set<UpdateQuestionsOrderRequestItem> questions

) {
}