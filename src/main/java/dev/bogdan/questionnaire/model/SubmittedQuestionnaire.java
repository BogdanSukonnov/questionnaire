package dev.bogdan.questionnaire.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(uniqueConstraints =
@UniqueConstraint(name = "uc_submitted_questionnaire_user_questionnaire", columnNames = {"questionnaire_id", "user_id"}))
public class SubmittedQuestionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Version
    private Integer version;

    @NotNull
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;

    @NotNull
    private LocalDateTime submittedAt;
    
}
