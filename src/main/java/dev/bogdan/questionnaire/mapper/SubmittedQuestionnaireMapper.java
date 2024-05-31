package dev.bogdan.questionnaire.mapper;

import dev.bogdan.questionnaire.dto.NewSubmittedQuestionnaireRequest;
import dev.bogdan.questionnaire.dto.SubmittedQuestionnaireDto;
import dev.bogdan.questionnaire.model.Questionnaire;
import dev.bogdan.questionnaire.model.SubmittedQuestionnaire;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SubmittedQuestionnaireMapper {

    public SubmittedQuestionnaire fromDto(NewSubmittedQuestionnaireRequest newSubmittedQuestionnaireRequest,
                                          Questionnaire questionnaire, LocalDateTime submittedAt) {
        SubmittedQuestionnaire submittedQuestionnaire = new SubmittedQuestionnaire();
        submittedQuestionnaire.setUserId(newSubmittedQuestionnaireRequest.userId());
        submittedQuestionnaire.setQuestionnaire(questionnaire);
        submittedQuestionnaire.setSubmittedAt(submittedAt);
        return submittedQuestionnaire;
    }

    public SubmittedQuestionnaireDto toDto(SubmittedQuestionnaire submittedQuestionnaire) {
        return new SubmittedQuestionnaireDto(
                submittedQuestionnaire.getId(),
                submittedQuestionnaire.getVersion(),
                submittedQuestionnaire.getUserId(),
                submittedQuestionnaire.getQuestionnaire().getId(),
                submittedQuestionnaire.getSubmittedAt()
        );
    }

}
