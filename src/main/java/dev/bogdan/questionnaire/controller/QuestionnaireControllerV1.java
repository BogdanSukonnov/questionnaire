package dev.bogdan.questionnaire.controller;

import dev.bogdan.questionnaire.dto.NewQuestionnaireRequest;
import dev.bogdan.questionnaire.dto.QuestionnaireDto;
import dev.bogdan.questionnaire.dto.UpdateQuestionnaireRequest;
import dev.bogdan.questionnaire.service.QuestionnaireService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/questionnaires")
@RequiredArgsConstructor
public class QuestionnaireControllerV1 {

    private final QuestionnaireService questionnaireService;

    @GetMapping(params = { "page", "size" })
    public Page<QuestionnaireDto> getQuestionnaires(@RequestParam("page") int page, @RequestParam("size") int size) {
        return questionnaireService.getAllQuestionnairesPaginated(page, size);
    }

    @PostMapping
    public QuestionnaireDto createQuestionnaire(@Valid @RequestBody NewQuestionnaireRequest newQuestionnaireRequest) {
        return questionnaireService.addQuestionnaire(newQuestionnaireRequest);
    }

    @PutMapping
    public QuestionnaireDto updateQuestionnaire(@Valid @RequestBody UpdateQuestionnaireRequest updateQuestionnaireRequest) {
        return questionnaireService.updateQuestionnaire(updateQuestionnaireRequest);
    }
}
