package dev.bogdan.questionnaire.repository;

import dev.bogdan.questionnaire.model.Question;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Override
    Optional<Question> findById(Long id);

    @Lock(LockModeType.OPTIMISTIC)
    List<Question> findAllById(Iterable<Long> ids);

    List<Question> findAllByQuestionnaireIdOrderByOrderNumber(long questionnaireId);

}
