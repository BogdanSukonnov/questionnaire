package dev.bogdan.questionnaire.repository;

import dev.bogdan.questionnaire.model.SubmittedQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmittedQuestionnaireRepository extends JpaRepository<SubmittedQuestionnaire, Long> {

    Optional<Object> findByQuestionnaireIdAndUserId(Long id, long userId);

}
