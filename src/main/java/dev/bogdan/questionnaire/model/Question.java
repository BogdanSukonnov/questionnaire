package dev.bogdan.questionnaire.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer version;

    @NotNull
    private String text;

    @NotNull
    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    String[] answers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionary_id")
    private Questionnaire questionnaire;

}
