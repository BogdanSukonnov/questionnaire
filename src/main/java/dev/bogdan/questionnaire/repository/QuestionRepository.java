package dev.bogdan.questionnaire.repository;

import dev.bogdan.questionnaire.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByQuestionnaireIdOrderByOrderNumber(long questionnaireId);

}
