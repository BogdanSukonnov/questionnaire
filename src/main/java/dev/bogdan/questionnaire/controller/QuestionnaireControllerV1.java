package dev.bogdan.questionnaire.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questionnaires")
public class QuestionnaireControllerV1 {

    @GetMapping
    public String getQuestionnaires() {
        return "Hello, World";
    }
}
