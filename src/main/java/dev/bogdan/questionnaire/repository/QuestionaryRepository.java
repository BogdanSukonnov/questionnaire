package dev.bogdan.questionnaire.repository;

import dev.bogdan.questionnaire.model.Questionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionaryRepository extends JpaRepository<Questionary, Long> {
}
