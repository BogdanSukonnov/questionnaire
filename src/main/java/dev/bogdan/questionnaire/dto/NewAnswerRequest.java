package dev.bogdan.questionnaire.dto;

import java.time.LocalDateTime;

public record NewAnswerRequest(
        Long questionId,
        Integer questionVersion,
        String text,
        Integer checkedAnswer,
        Integer[] checkedAnswers,
        LocalDateTime submittedAt
) {
}