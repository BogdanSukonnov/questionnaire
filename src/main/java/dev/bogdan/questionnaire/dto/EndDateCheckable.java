package dev.bogdan.questionnaire.dto;

import java.time.LocalDateTime;

public interface EndDateCheckable {
    LocalDateTime endDate();
    LocalDateTime startDate();

    default void checkEndDate() {
        if (endDate() != null && startDate() != null && endDate().isBefore(startDate())) {
            throw new IllegalArgumentException("End date must be after start date");
        }
    }
}
