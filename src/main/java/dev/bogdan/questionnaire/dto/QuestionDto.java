package dev.bogdan.questionnaire.dto;

public record QuestionDto(
        Long id,
        Integer version,
        Integer orderNumber,
        String text,
        String type,
        String[] answers
) {
}