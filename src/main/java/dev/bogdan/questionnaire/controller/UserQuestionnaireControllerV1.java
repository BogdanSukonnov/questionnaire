package dev.bogdan.questionnaire.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-questionnaires")
public class UserQuestionnaireControllerV1 {

    @GetMapping
    public String getQuestionnaires() {
        return "Hello, World";
    }
}
