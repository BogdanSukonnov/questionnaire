package dev.bogdan.questionnaire.controller;

import dev.bogdan.questionnaire.dto.QuestionnaireDto;
import dev.bogdan.questionnaire.service.ActiveQuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/active-questionnaires")
@RequiredArgsConstructor
public class ActiveQuestionnaireControllerV1 {

    private final ActiveQuestionnaireService activeQuestionnaireService;

    @GetMapping(params = { "user_id" })
    public List<QuestionnaireDto> getActiveQuestionnaires(@RequestParam("user_id") long userId) {
        return activeQuestionnaireService.getActiveQuestionnaires(userId);
    }

}
