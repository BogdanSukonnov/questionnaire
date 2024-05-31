package dev.bogdan.questionnaire.mapper;

import dev.bogdan.questionnaire.dto.NewQuestionnaireRequest;
import dev.bogdan.questionnaire.dto.QuestionnaireDto;
import dev.bogdan.questionnaire.model.Questionnaire;
import org.springframework.stereotype.Component;

@Component
public class QuestionnaireMapper {

    public QuestionnaireDto toDto(Questionnaire questionnaire) {
        return new QuestionnaireDto(
                questionnaire.getId(),
                questionnaire.getVersion(),
                questionnaire.getTitle(),
                questionnaire.getStartDate(),
                questionnaire.getEndDate(),
                questionnaire.getDescription()
        );
    }

    public Questionnaire fromDto(NewQuestionnaireRequest newQuestionnaireRequest) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setTitle(newQuestionnaireRequest.title());
        questionnaire.setStartDate(newQuestionnaireRequest.startDate());
        questionnaire.setEndDate(newQuestionnaireRequest.endDate());
        questionnaire.setDescription(newQuestionnaireRequest.description());
        return questionnaire;
    }

}
