package dev.bogdan.questionnaire.controller;

import dev.bogdan.questionnaire.dto.AnswerDto;
import dev.bogdan.questionnaire.dto.NewAnswerRequest;
import dev.bogdan.questionnaire.dto.UpdateAnswerRequest;
import dev.bogdan.questionnaire.service.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/answers")
@RequiredArgsConstructor
public class AnswerControllerV1 {

    private final AnswerService answerService;

    @PostMapping(params = {"user_id"})
    public AnswerDto addAnswer(@Valid @RequestBody NewAnswerRequest newAnswerRequest,
                               @RequestParam("user_id") long userId) {
        return answerService.addAnswer(newAnswerRequest, userId);
    }

    @PutMapping(params = {"user_id"})
    public AnswerDto updateAnswer(@Valid @RequestBody UpdateAnswerRequest updateAnswerRequest,
                               @RequestParam("user_id") long userId) {
        return answerService.updateAnswer(updateAnswerRequest, userId);
    }

    @DeleteMapping(name = "/{id}", params = {"user_id"})
    public void deleteAnswer(@PathVariable long id, @RequestParam("user_id") long userId) {
        answerService.deleteAnswer(id, userId);
    }

}
