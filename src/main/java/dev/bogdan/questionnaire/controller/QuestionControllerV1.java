package dev.bogdan.questionnaire.controller;

import dev.bogdan.questionnaire.dto.NewQuestionRequest;
import dev.bogdan.questionnaire.dto.QuestionDto;
import dev.bogdan.questionnaire.dto.UpdateQuestionRequest;
import dev.bogdan.questionnaire.dto.UpdateQuestionsOrderRequest;
import dev.bogdan.questionnaire.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionControllerV1 {

    private final QuestionService questionService;

    @Operation(summary = "Get questions by questionnaire_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found questions",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = @Content)})
    @GetMapping("/{questionnaireId}")
    public List<QuestionDto> getQuestions(@PathVariable Long questionnaireId) {
        return questionService.getAllQuestionsByQuestionnaireId(questionnaireId);
    }

    @Operation(summary = "Add new question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = @Content)})
    @PostMapping
    public QuestionDto createQuestion(@Valid @RequestBody NewQuestionRequest newQuestionRequest) {
        return questionService.addQuestion(newQuestionRequest);
    }

    @Operation(summary = "Edit question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = @Content)})
    @PutMapping
    public QuestionDto updateQuestion(@Valid @RequestBody UpdateQuestionRequest updateQuestionRequest) {
        return questionService.updateQuestion(updateQuestionRequest);
    }

    @Operation(summary = "Delete question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }

    @Operation(summary = "Update questions order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Questions order updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = @Content)})
    @PutMapping("/update-order")
    public List<QuestionDto> updateOrderNumbers(
            @Valid @RequestBody UpdateQuestionsOrderRequest updateQuestionsOrderRequest) {
        return questionService.updateQuestionsOrder(updateQuestionsOrderRequest);
    }

}
