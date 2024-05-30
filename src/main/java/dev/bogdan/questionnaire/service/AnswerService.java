package dev.bogdan.questionnaire.service;

import dev.bogdan.questionnaire.dto.AnswerDto;
import dev.bogdan.questionnaire.dto.NewAnswerRequest;
import dev.bogdan.questionnaire.dto.UpdateAnswerRequest;
import dev.bogdan.questionnaire.mapper.AnswerMapper;
import dev.bogdan.questionnaire.model.Answer;
import dev.bogdan.questionnaire.model.Question;
import dev.bogdan.questionnaire.model.QuestionType;
import dev.bogdan.questionnaire.repository.AnswerRepository;
import dev.bogdan.questionnaire.repository.QuestionRepository;
import dev.bogdan.questionnaire.repository.SubmittedQuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    private final SubmittedQuestionnaireRepository submittedQuestionnaireRepository;

    private final AnswerMapper answerMapper;

    @Transactional
    public AnswerDto addAnswer(NewAnswerRequest newAnswerRequest, Long userId) {
        Question question = checkQuestion(newAnswerRequest.questionId(), newAnswerRequest.questionVersion());
        checkSubmittedQuestionnaire(userId, question);
        checkRequestWithType(question.getType(), newAnswerRequest.text(), newAnswerRequest.checkedAnswer(), newAnswerRequest.checkedAnswers());
        Answer answer = answerRepository.save(answerMapper.toAnswer(newAnswerRequest, userId, question));
        return answerMapper.toDto(answer);
    }

    @Transactional
    public AnswerDto updateAnswer(UpdateAnswerRequest updateAnswerRequest, long userId) {
        Question question = checkQuestion(updateAnswerRequest.questionId(), updateAnswerRequest.questionVersion());
        checkSubmittedQuestionnaire(userId, question);
        checkRequestWithType(question.getType(), updateAnswerRequest.text(), updateAnswerRequest.checkedAnswer(), updateAnswerRequest.checkedAnswers());
        Answer answer = answerRepository.findById(updateAnswerRequest.id())
                .orElseThrow(() -> new IllegalArgumentException("Answer not found"));
        if (Objects.equals(answer.getUserId(), userId)) {
            throw new IllegalArgumentException("Answer does not belong to the user");
        }
        answer.setVersion(updateAnswerRequest.version());
        answer.setUserId(userId);
        answer.setQuestion(question);
        switch (question.getType()) {
            case TEXT -> {
                answer.setText(updateAnswerRequest.text());
                answer.setCheckedAnswer(null);
                answer.setCheckedAnswers(null);
            }
            case SINGLE_CHOICE -> {
                answer.setText(null);
                answer.setCheckedAnswer(updateAnswerRequest.checkedAnswer());
                answer.setCheckedAnswers(null);
            }
            case MULTIPLE_CHOICE -> {
                answer.setText(null);
                answer.setCheckedAnswer(null);
                answer.setCheckedAnswers(updateAnswerRequest.checkedAnswers());
            }
        }
        return answerMapper.toDto(answer);
    }

    private void checkSubmittedQuestionnaire(long userId, Question question) {
        submittedQuestionnaireRepository.findByQuestionnaireIdAndUserId(question.getQuestionnaire().getId(), userId)
                .ifPresent(submittedQuestionnaire -> {
                    throw new IllegalArgumentException("Questionnaire is already submitted");
                });
    }

    @Transactional
    public void deleteAnswer(Long id, Long userId) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Answer not found"));
        if (!Objects.equals(answer.getUserId(), userId)) {
            throw new IllegalArgumentException("Answer does not belong to the user");
        }
        Question question = questionRepository.findById(answer.getQuestion().getId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));
        checkSubmittedQuestionnaire(userId, question);
        answerRepository.deleteById(id);
    }

    private Question checkQuestion(Long questionId, Object questionVersion) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));
        if (!Objects.equals(question.getVersion(), questionVersion)) {
            throw new IllegalArgumentException("Question version is outdated");
        }
        return question;
    }

    private void checkRequestWithType(QuestionType type, String text, Integer integer, Integer[] integers) {
        switch (type) {
            case TEXT -> {
                if (text == null) {
                    throw new IllegalArgumentException("Text is required");
                }
            }
            case SINGLE_CHOICE -> {
                if (integer == null) {
                    throw new IllegalArgumentException("Checked answer is required");
                }
            }
            case MULTIPLE_CHOICE -> {
                if (integers == null || integers.length == 0) {
                    throw new IllegalArgumentException("Checked answers are required");
                }
            }
        }
    }

}
