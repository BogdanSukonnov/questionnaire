package dev.bogdan.questionnaire.service;

import dev.bogdan.questionnaire.dto.NewQuestionnaireRequest;
import dev.bogdan.questionnaire.dto.QuestionnaireDto;
import dev.bogdan.questionnaire.dto.UpdateQuestionnaireRequest;
import dev.bogdan.questionnaire.mapper.QuestionnaireMapper;
import dev.bogdan.questionnaire.model.Questionnaire;
import dev.bogdan.questionnaire.repository.QuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;

    private final QuestionnaireMapper questionnaireMapper;

    @Transactional(readOnly = true)
    public Page<QuestionnaireDto> getAllQuestionnairesPaginated(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Questionnaire> activeQuestionnaires = questionnaireRepository.findAll(pageable);
        return activeQuestionnaires.map(questionnaireMapper::fromQuestionary);
    }

    @Transactional(readOnly = true)
    public Page<QuestionnaireDto> getActiveQuestionnairesPaginated(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Questionnaire> activeQuestionnaires = questionnaireRepository.findAll(pageable);
        return activeQuestionnaires.map(questionnaireMapper::fromQuestionary);
    }

    @Transactional
    public QuestionnaireDto addQuestionnaire(NewQuestionnaireRequest newQuestionnaireRequest) {
        newQuestionnaireRequest.checkEndDate();
        Questionnaire savedQuestionnaire = questionnaireRepository.save(questionnaireMapper.toQuestionary(newQuestionnaireRequest));
        return questionnaireMapper.fromQuestionary(savedQuestionnaire);
    }

    @Transactional
    public QuestionnaireDto updateQuestionnaire(UpdateQuestionnaireRequest updateQuestionnaireRequest) {
        updateQuestionnaireRequest.checkEndDate();
        Questionnaire questionnaire = questionnaireRepository.findById(updateQuestionnaireRequest.id())
                .orElseThrow(() -> new IllegalArgumentException("Questionnaire not found"));
        if (!questionnaire.getStartDate().isEqual(updateQuestionnaireRequest.startDate())) {
            throw new IllegalArgumentException("Start date cannot be changed");
        }
        questionnaire.setVersion(updateQuestionnaireRequest.version());
        questionnaire.setTitle(updateQuestionnaireRequest.title());
        questionnaire.setStartDate(updateQuestionnaireRequest.startDate());
        questionnaire.setEndDate(updateQuestionnaireRequest.endDate());
        questionnaire.setDescription(updateQuestionnaireRequest.description());
        return questionnaireMapper.fromQuestionary(questionnaire);
    }

}
