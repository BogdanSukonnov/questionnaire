package dev.bogdan.questionnaire.controller;

import dev.bogdan.questionnaire.dto.NewSubmittedQuestionnaireRequest;
import dev.bogdan.questionnaire.dto.SubmittedQuestionnaireDto;
import dev.bogdan.questionnaire.dto.UserQuestionnaireDto;
import dev.bogdan.questionnaire.service.ActiveQuestionnaireService;
import dev.bogdan.questionnaire.service.SubmittedQuestionnaireService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/submitted-questionnaires")
@RequiredArgsConstructor
public class SubmittedQuestionnaireControllerV1 {

    private final ActiveQuestionnaireService activeQuestionnaireService;

    private final SubmittedQuestionnaireService submittedQuestionnaireService;

    @GetMapping(params = { "user_id" })
    public List<UserQuestionnaireDto> getUserQuestionary(@RequestParam("user_id") long userId) {
        return activeQuestionnaireService.getSubmittedQuestionnaires(userId);
    }

    @PostMapping(params = { "user_id" })
    public SubmittedQuestionnaireDto submitQuestionnaire(@Valid @RequestBody NewSubmittedQuestionnaireRequest newSubmittedQuestionnaireRequest) {
        return submittedQuestionnaireService.addSubmittedQuestionnaire(newSubmittedQuestionnaireRequest);
    }

}
