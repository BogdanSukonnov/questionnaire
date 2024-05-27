package dev.bogdan.questionnaire.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.Type;

@Entity
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private Long version;

    @NotNull
    private String text;

    @NotNull
    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    String[] answers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionary_id", nullable = false, insertable = false, updatable = false)
    private Questionary questionary;

}
