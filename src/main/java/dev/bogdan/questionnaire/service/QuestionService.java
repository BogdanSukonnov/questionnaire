package dev.bogdan.questionnaire.service;

import dev.bogdan.questionnaire.dto.NewQuestionRequest;
import dev.bogdan.questionnaire.dto.QuestionDto;
import dev.bogdan.questionnaire.dto.UpdateQuestionRequest;
import dev.bogdan.questionnaire.mapper.QuestionMapper;
import dev.bogdan.questionnaire.model.Question;
import dev.bogdan.questionnaire.model.Questionnaire;
import dev.bogdan.questionnaire.repository.QuestionRepository;
import dev.bogdan.questionnaire.repository.QuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

//    @Transactional
//    public List<QuestionDto> updateOrderNumbers(UpdateQuestionsOrderNumbersRequest updateQuestionsOrderNumbersRequest) {
//        List<Question> questions = questionRepository.findAllById(updateQuestionsOrderNumbersRequest.questionIds());
//        for (Question question : questions) {
//            question.setOrderNumber(updateQuestionsOrderNumbersRequest.orderNumbers().get(question.getId()));
//        }
//        return questions.stream().map(questionMapper::toDto).toList();
//    }
}
