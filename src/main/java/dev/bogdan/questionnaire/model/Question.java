package dev.bogdan.questionnaire.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(uniqueConstraints =
@UniqueConstraint(name = "uc_question_order_questionnaire", columnNames = {"questionnaire_id", "order_number"}))
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    private Integer version;

    private Integer orderNumber;

    @NotNull
    private String text;

    @NotNull
    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    String[] answers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "question_id")
    private List<Answer> userAnswers = new ArrayList<>();

}
