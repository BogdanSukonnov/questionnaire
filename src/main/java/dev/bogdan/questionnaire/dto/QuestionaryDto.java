package dev.bogdan.questionnaire.dto;

import dev.bogdan.questionnaire.model.Questionary;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public record QuestionaryDto(
        Long id,
        Long version,
        String title,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String description,
        List<QuestionDto> questions
) {

    public static QuestionaryDto fromQuestionary(Questionary questionary) {
        return new QuestionaryDto(
                questionary.getId(),
                questionary.getVersion(),
                questionary.getTitle(),
                questionary.getStartDate(),
                questionary.getEndDate(),
                questionary.getDescription(),
                questionary.getQuestions() == null ? Collections.EMPTY_LIST : questionary.getQuestions().stream()
                        .map(QuestionDto::fromQuestion)
                        .toList()
        );
    }

}
