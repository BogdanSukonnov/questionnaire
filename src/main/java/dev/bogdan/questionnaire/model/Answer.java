package dev.bogdan.questionnaire.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(uniqueConstraints =
@UniqueConstraint(name = "uc_answer_user_question", columnNames = {"question_id", "user_id"}))
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer version;

    @NotNull
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @NotNull
    private Question question;

    private String text;

    private Integer checkedAnswer;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    Integer[] checkedAnswers;

    @NotNull
    private LocalDateTime submittedAt;

}
