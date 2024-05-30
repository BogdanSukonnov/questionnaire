package dev.bogdan.questionnaire.dto;

import java.util.List;

public record UserQuestionnaireDto(
        QuestionnaireDto questionnaire,
        List<QuestionDto> questions,
        List<AnswerDto> answers
) {
}
