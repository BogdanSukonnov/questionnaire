package dev.bogdan.questionnaire.service;

import dev.bogdan.questionnaire.dto.QuestionaryDto;
import dev.bogdan.questionnaire.model.Questionary;
import dev.bogdan.questionnaire.repository.QuestionaryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {

    private final QuestionaryRepository questionaryRepository;

    public Page<QuestionaryDto> getActiveQuestionnairesPaginated(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Questionary> activeQuestionnaires = questionaryRepository.findAll(pageable);
        return activeQuestionnaires.map(QuestionaryDto::fromQuestionary);
    }

    @Transactional
    public QuestionaryDto addQuestionnaire(Questionary questionary) {
        Questionary savedQuestionary = questionaryRepository.save(questionary);
        return QuestionaryDto.fromQuestionary(savedQuestionary);
    }
}
