package dev.bogdan.questionnaire.service;

import dev.bogdan.questionnaire.dto.QuestionnaireDto;
import dev.bogdan.questionnaire.dto.UserQuestionnaireDto;
import dev.bogdan.questionnaire.mapper.AnswerMapper;
import dev.bogdan.questionnaire.mapper.QuestionMapper;
import dev.bogdan.questionnaire.mapper.QuestionnaireMapper;
import dev.bogdan.questionnaire.model.Answer;
import dev.bogdan.questionnaire.model.Question;
import dev.bogdan.questionnaire.model.Questionnaire;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActiveQuestionnaireService {

    private final EntityManager entityManager;

    private final QuestionnaireMapper questionnaireMapper;

    private final QuestionMapper questionMapper;

    private final AnswerMapper answerMapper;

    @Transactional(readOnly = true)
    public List<QuestionnaireDto> getActiveQuestionnaires(Long userId) {

        List<Questionnaire> resultList = entityManager.createQuery("""
                        SELECT q
                        FROM Questionnaire q
                        LEFT JOIN SubmittedQuestionnaire sq
                        ON q.id = sq.questionnaire.id
                        AND sq.userId = :userId
                        WHERE sq.id IS NULL
                        AND q.startDate > :currentDate
                        AND (q.endDate is null
                        OR q.endDate < :currentDate)
                        ORDER BY q.startDate
                        """, Questionnaire.class)
                .setParameter("userId", userId)
                .setParameter("currentDate", LocalDateTime.now())
                .getResultList();

        return resultList.stream().map(questionnaireMapper::toDto).toList();

    }

    @Transactional(readOnly = true)
    public UserQuestionnaireDto getUserQuestionnaire(Long questionnaireId, Long userId) {

        Questionnaire questionnaire = entityManager.createQuery("""
                        SELECT q
                        FROM Questionnaire q
                        WHERE q.id = :questionnaireId
                        """, Questionnaire.class)
                .setParameter("questionnaireId", questionnaireId)
                .getSingleResult();

        List<Question> questions = entityManager.createQuery("""
                        SELECT q
                        FROM Question q
                        WHERE q.questionnaire.id = :questionnaireId
                        ORDER BY q.orderNumber
                        """, Question.class)
                .setParameter("questionnaireId", questionnaireId)
                .getResultList();

        List<Answer> answers = entityManager.createQuery("""
                        SELECT a
                        FROM Answer a
                        WHERE a.question.id in :questionIds
                        AND a.userId = :userId
                        """, Answer.class)
                .setParameter("questionIds", questions.stream().map(Question::getId).toList())
                .setParameter("userId", userId)
                .getResultList();

        return new UserQuestionnaireDto(questionnaireMapper.toDto(questionnaire),
                questions.stream().map(questionMapper::toDto).toList(),
                answers.stream().map(answerMapper::toDto).toList());

    }

    @Transactional(readOnly = true)
    public List<UserQuestionnaireDto> getSubmittedQuestionnaires(long userId) {

        List<Questionnaire> questionnaires = entityManager.createQuery("""
                        SELECT q from Questionnaire q
                        JOIN SubmittedQuestionnaire sq ON q.id = sq.questionnaire.id
                        WHERE sq.userId = :userId
                        ORDER BY sq.submittedAt
                        """, Questionnaire.class)
                .setParameter("userId", userId)
                .getResultList();

        List<Question> questions = entityManager.createQuery("""
                        SELECT q
                        FROM Question q
                        WHERE q.questionnaire.id in :questionnaireIds
                        ORDER BY q.orderNumber
                        """, Question.class)
                .setParameter("questionnaireIds", questionnaires.stream().map(Questionnaire::getId).toList())
                .getResultList();

        Map<Long, List<Question>> questionnaireIdToQuestionsMap = questions.stream()
                .collect(Collectors.groupingBy(question -> question.getQuestionnaire().getId()));

        List<Answer> answers = entityManager.createQuery("""
                        SELECT a
                        FROM Answer a
                        WHERE a.question.id in :questionIds
                        AND a.userId = :userId
                        """, Answer.class)
                .setParameter("questionIds", questions.stream().map(Question::getId).toList())
                .setParameter("userId", userId)
                .getResultList();

        Map<Long, List<Answer>> questionIdToAnswersMap = answers.stream()
                .collect(Collectors.groupingBy(answer -> answer.getQuestion().getId()));

        return questionnaires.stream().map(questionnaire -> {
            List<Question> questionnaireQuestions = questionnaireIdToQuestionsMap.get(questionnaire.getId());
            List<Answer> questionnaireAnswers = questionnaireQuestions.stream()
                    .map(question -> questionIdToAnswersMap.get(question.getId()))
                    .flatMap(List::stream)
                    .toList();
            return new UserQuestionnaireDto(questionnaireMapper.toDto(questionnaire),
                    questionnaireQuestions.stream().map(questionMapper::toDto).toList(),
                    questionnaireAnswers.stream().map(answerMapper::toDto).toList());
        }).toList();

    }
}
