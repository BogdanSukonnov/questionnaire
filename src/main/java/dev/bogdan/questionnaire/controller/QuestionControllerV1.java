package dev.bogdan.questionnaire.controller;

import dev.bogdan.questionnaire.dto.NewQuestionRequest;
import dev.bogdan.questionnaire.dto.QuestionDto;
import dev.bogdan.questionnaire.dto.UpdateQuestionRequest;
import dev.bogdan.questionnaire.dto.UpdateQuestionsOrderRequest;
import dev.bogdan.questionnaire.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionControllerV1 {

    private final QuestionService questionService;

    @GetMapping("/{questionnaireId}")
    public List<QuestionDto> getQuestions(@PathVariable Long questionnaireId) {
        return questionService.getAllQuestionsByQuestionnaireId(questionnaireId);
    }

    @PostMapping
    public QuestionDto createQuestion(@Valid @RequestBody NewQuestionRequest newQuestionRequest) {
        return questionService.addQuestion(newQuestionRequest);
    }

    @PutMapping
    public QuestionDto updateQuestion(@Valid @RequestBody UpdateQuestionRequest updateQuestionRequest) {
        return questionService.updateQuestion(updateQuestionRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }

    @PutMapping("/update-order")
    public List<QuestionDto> updateOrderNumbers(
            @Valid @RequestBody UpdateQuestionsOrderRequest updateQuestionsOrderRequest) {
        return questionService.updateQuestionsOrder(updateQuestionsOrderRequest);
    }

}
