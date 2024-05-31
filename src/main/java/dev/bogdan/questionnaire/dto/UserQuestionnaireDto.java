package dev.bogdan.questionnaire.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserQuestionnaireDto(
        @NotNull QuestionnaireDto questionnaire,
        @NotNull List<QuestionDto> questions,
        @NotNull List<AnswerDto> answers
) {
}
