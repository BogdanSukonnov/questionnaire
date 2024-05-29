package dev.bogdan.questionnaire.mapper;

import dev.bogdan.questionnaire.dto.NewQuestionRequest;
import dev.bogdan.questionnaire.dto.QuestionDto;
import dev.bogdan.questionnaire.model.Question;
import dev.bogdan.questionnaire.model.Questionnaire;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public QuestionDto toDto(Question question) {
        return new QuestionDto(question.getId(), question.getVersion(), question.getOrderNumber(), question.getText(),
                question.getType().name(), question.getAnswers());
    }

    public Question toQuestion(NewQuestionRequest newQuestionRequest, Questionnaire questionnaire) {
        Question question = new Question();
        question.setOrderNumber(newQuestionRequest.orderNumber());
        question.setText(newQuestionRequest.text());
        question.setType(newQuestionRequest.type());
        question.setAnswers(newQuestionRequest.answers());
        question.setQuestionnaire(questionnaire);
        return question;
    }
}
