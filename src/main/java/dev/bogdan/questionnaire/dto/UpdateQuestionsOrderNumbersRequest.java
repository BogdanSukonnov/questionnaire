package dev.bogdan.questionnaire.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateQuestionsOrderNumbersRequest(

        @NotNull Long questionnaireId,

        @NotNull List<UpdateQuestionsOrderNumbersRequestItem> questions

) {
}