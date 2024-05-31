package dev.bogdan.questionnaire.repository;

import dev.bogdan.questionnaire.model.Questionnaire;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<Questionnaire> findById(Long id);

}
