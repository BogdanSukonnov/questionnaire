package dev.bogdan.questionnaire.controller;

import dev.bogdan.questionnaire.dto.QuestionnaireDto;
import dev.bogdan.questionnaire.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/active-questionnaires")
@RequiredArgsConstructor
public class ActiveQuestionnaireControllerV1 {

    private final QuestionnaireService questionnaireService;

    @GetMapping(params = { "page", "size" })
    public Page<QuestionnaireDto> getActiveQuestionnaires(@RequestParam("page") int page, @RequestParam("size") int size) {
        return questionnaireService.getActiveQuestionnairesPaginated(page, size);
    }

}
