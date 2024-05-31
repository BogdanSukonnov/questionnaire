package dev.bogdan.questionnaire.repository;

import dev.bogdan.questionnaire.model.Answer;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Override
    Optional<Answer> findById(Long id);

}
