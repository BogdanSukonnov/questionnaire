package dev.bogdan.questionnaire.controller;

import dev.bogdan.questionnaire.dto.UserQuestionnaireDto;
import dev.bogdan.questionnaire.service.ActiveQuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-questionnaires")
@RequiredArgsConstructor
public class UserQuestionnaireControllerV1 {

    private final ActiveQuestionnaireService activeQuestionnaireService;

    @GetMapping(name = "/{questionnaire_id}", params = { "user_id" })
    public UserQuestionnaireDto getUserQuestionary(@PathVariable long questionnaireId,
                                                   @RequestParam("user_id") long userId) {
        return activeQuestionnaireService.getUserQuestionnaire(questionnaireId, userId);
    }

}
