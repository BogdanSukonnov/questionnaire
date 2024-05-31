package dev.bogdan.questionnaire.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Questionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    private Integer version;

    @NotNull
    private String title;

    @NotNull
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "questionnaire_id")
    @OrderBy("orderNumber")
    private List<Question> questions = new ArrayList<>();

}
