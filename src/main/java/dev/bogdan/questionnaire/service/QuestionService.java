package dev.bogdan.questionnaire.service;

import dev.bogdan.questionnaire.dto.*;
import dev.bogdan.questionnaire.mapper.QuestionMapper;
import dev.bogdan.questionnaire.model.Question;
import dev.bogdan.questionnaire.model.Questionnaire;
import dev.bogdan.questionnaire.repository.QuestionRepository;
import dev.bogdan.questionnaire.repository.QuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    private final QuestionnaireRepository questionnaireRepository;

    @Transactional(readOnly = true)
    public List<QuestionDto> getAllQuestionsByQuestionnaireId(long questionnaireId) {
        List<Question> questionList = questionRepository.findAllByQuestionnaireIdOrderByOrderNumber(questionnaireId);
        return questionList.stream().map(questionMapper::toDto).toList();
    }

    @Transactional
    public QuestionDto addQuestion(NewQuestionRequest newQuestionRequest) {
        Questionnaire questionnaire = questionnaireRepository.findById(newQuestionRequest.questionnaireId())
                .orElseThrow(() -> new IllegalArgumentException("Questionnaire not found"));
        Question savedQuestion = questionRepository.save(questionMapper.toQuestion(newQuestionRequest, questionnaire));
        return questionMapper.toDto(savedQuestion);
    }

    @Transactional
    public QuestionDto updateQuestion(UpdateQuestionRequest updateQuestionRequest) {
        Question question = questionRepository.findById(updateQuestionRequest.id())
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));
        question.setOrderNumber(updateQuestionRequest.orderNumber());
        question.setText(updateQuestionRequest.text());
        question.setType(updateQuestionRequest.type());
        question.setAnswers(updateQuestionRequest.answers());
        return questionMapper.toDto(question);
    }

    @Transactional
    public void deleteQuestion(long id) {
        questionRepository.deleteById(id);
    }

    @Transactional
    public List<QuestionDto> updateQuestionsOrder(UpdateQuestionsOrderRequest updateQuestionsOrderRequest) {
        List<Question> questions = questionRepository.findAllById(updateQuestionsOrderRequest.questions()
                .stream()
                .map(UpdateQuestionsOrderRequestItem::questionId)
                .collect(Collectors.toSet()));
        Map<Long, Question> questionMap = questions.stream().collect(Collectors.toMap(Question::getId, question -> question));
        for (UpdateQuestionsOrderRequestItem item : updateQuestionsOrderRequest.questions()) {
            Question question = questionMap.get(item.questionId());
            if (question == null) {
                throw new IllegalArgumentException("Question not found");
            }
            question.setVersion(item.version());
            question.setOrderNumber(item.orderNumber());
        }
        return questions.stream().map(questionMapper::toDto).toList();
    }
}
