package dev.bogdan.questionnaire.mapper;

import dev.bogdan.questionnaire.dto.AnswerDto;
import dev.bogdan.questionnaire.dto.NewAnswerRequest;
import dev.bogdan.questionnaire.model.Answer;
import dev.bogdan.questionnaire.model.Question;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper {

    public AnswerDto toDto(Answer answer) {
        return new AnswerDto(
                answer.getId(),
                answer.getVersion(),
                answer.getText(),
                answer.getCheckedAnswer(),
                answer.getCheckedAnswers(),
                answer.getSubmittedAt()
        );
    }

    public Answer toAnswer(NewAnswerRequest newQuestionRequest, Long userId, Question question) {
        Answer answer = new Answer();
        answer.setUserId(userId);
        answer.setQuestion(question);
        switch (question.getType()) {
            case TEXT -> answer.setText(newQuestionRequest.text());
            case SINGLE_CHOICE -> answer.setCheckedAnswer(newQuestionRequest.checkedAnswer());
            case MULTIPLE_CHOICE -> answer.setCheckedAnswers(newQuestionRequest.checkedAnswers());
        }
        answer.setSubmittedAt(newQuestionRequest.submittedAt());
        return answer;
    }

}
