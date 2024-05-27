package dev.bogdan.questionnaire.dto;

import dev.bogdan.questionnaire.model.Question;

public record QuestionDto(
        Long id,
        Long version,
        String text,
        String type,
        String[] answers
) {

    public static QuestionDto fromQuestion(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getVersion(),
                question.getText(),
                question.getType().name(),
                question.getAnswers()
        );
    }

}