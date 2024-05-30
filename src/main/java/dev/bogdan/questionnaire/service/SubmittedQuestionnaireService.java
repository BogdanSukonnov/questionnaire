package dev.bogdan.questionnaire.service;

import dev.bogdan.questionnaire.dto.NewSubmittedQuestionnaireRequest;
import dev.bogdan.questionnaire.dto.SubmittedQuestionnaireDto;
import dev.bogdan.questionnaire.mapper.QuestionnaireMapper;
import dev.bogdan.questionnaire.repository.SubmittedQuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubmittedQuestionnaireService {

    private final SubmittedQuestionnaireRepository submittedQuestionnaireRepository;

    private final QuestionnaireMapper questionnaireMapper;

    @Transactional
    public SubmittedQuestionnaireDto addSubmittedQuestionnaire(NewSubmittedQuestionnaireRequest newSubmittedQuestionnaireRequest) {
        return null;
//        Questionnaire questionnaire = questionnaireRepository.findById(newSubmittedQuestionnaireRequest.questionnaireId())
//                .orElseThrow(() -> new IllegalArgumentException("Questionnaire not found"));
//        SubmittedQuestionnaire savedSubmittedQuestionnaire = submittedQuestionnaireRepository.save(submittedQuestionnaireMapper.toSubmittedQuestionnaire(newSubmittedQuestionnaireRequest, questionnaire));
//        return submittedQuestionnaireMapper.fromSubmittedQuestionnaire(savedSubmittedQuestionnaire);
    }


}
