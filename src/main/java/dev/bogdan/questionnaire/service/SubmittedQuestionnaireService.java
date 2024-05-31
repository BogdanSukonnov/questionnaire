package dev.bogdan.questionnaire.service;

import dev.bogdan.questionnaire.dto.NewSubmittedQuestionnaireRequest;
import dev.bogdan.questionnaire.dto.SubmittedQuestionnaireDto;
import dev.bogdan.questionnaire.mapper.SubmittedQuestionnaireMapper;
import dev.bogdan.questionnaire.model.Questionnaire;
import dev.bogdan.questionnaire.model.SubmittedQuestionnaire;
import dev.bogdan.questionnaire.repository.QuestionnaireRepository;
import dev.bogdan.questionnaire.repository.SubmittedQuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SubmittedQuestionnaireService {

    private final SubmittedQuestionnaireRepository submittedQuestionnaireRepository;

    private final SubmittedQuestionnaireMapper submittedQuestionnaireMapper;

    private final QuestionnaireRepository questionnaireRepository;

    @Transactional
    public SubmittedQuestionnaireDto addSubmittedQuestionnaire(NewSubmittedQuestionnaireRequest newSubmittedQuestionnaireRequest) {
        Questionnaire questionnaire = questionnaireRepository.findById(newSubmittedQuestionnaireRequest.questionnaireId())
                .orElseThrow(() -> new IllegalArgumentException("Questionnaire not found"));
        if (!Objects.equals(newSubmittedQuestionnaireRequest.questionnaireVersion(), questionnaire.getVersion())) {
            throw new IllegalArgumentException("Questionnaire version mismatch");
        }
        SubmittedQuestionnaire submittedQuestionnaire = submittedQuestionnaireRepository.save(submittedQuestionnaireMapper.fromDto(newSubmittedQuestionnaireRequest,
                questionnaire, LocalDateTime.now()));
        return submittedQuestionnaireMapper.toDto(submittedQuestionnaire);
    }

}
