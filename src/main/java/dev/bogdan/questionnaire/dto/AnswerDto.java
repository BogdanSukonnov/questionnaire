package dev.bogdan.questionnaire.dto;

import java.time.LocalDateTime;

public record AnswerDto(
        Long id,
        Integer version,
        String text,
        Integer checkedAnswer,
        Integer[] checkedAnswers,
        LocalDateTime submittedAt
) {
}