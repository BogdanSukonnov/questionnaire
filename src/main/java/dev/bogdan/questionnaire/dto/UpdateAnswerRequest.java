package dev.bogdan.questionnaire.dto;

import java.time.LocalDateTime;

public record UpdateAnswerRequest(
        Long questionId,
        Integer questionVersion,
        Long id,
        Integer version,
        String text,
        Integer checkedAnswer,
        Integer[] checkedAnswers,
        LocalDateTime submittedAt
) {
}