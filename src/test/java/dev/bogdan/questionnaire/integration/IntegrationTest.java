package dev.bogdan.questionnaire.integration;

import dev.bogdan.questionnaire.dto.NewQuestionnaireRequest;
import dev.bogdan.questionnaire.dto.QuestionnaireDto;
import dev.bogdan.questionnaire.model.QuestionType;
import dev.bogdan.questionnaire.service.QuestionnaireService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest
public class IntegrationTest {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Container
    @ServiceConnection
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    @Test
    public void addQuestionaryTest() {
        String title = "Test questionary";
        String description = "Test description";
        LocalDateTime startDate = LocalDateTime.of(2025, 5, 27, 17, 47);
        LocalDateTime endDate = LocalDateTime.of(2025, 6, 27, 17, 47);

        String q1Text = "question 1";
        QuestionType q1Type = QuestionType.TEXT;

        String q2Text = "question 2";
        QuestionType q2Type = QuestionType.MULTIPLE_CHOICE;
        String q2a1 = "answer 1";
        String q2a2 = "answer 2";

//        Question q1 = new Question();
//        q1.setText(q1Text);
//        q1.setType(q1Type);
//
//        Question q2 = new Question();
//        q2.setText(q2Text);
//        q2.setType(q2Type);
//        q2.setAnswers(new String[]{q2a1, q2a2});
        NewQuestionnaireRequest newQuestionnaireRequest = new NewQuestionnaireRequest(title, startDate, endDate, description);
//        questionnaire.setQuestions(List.of(q1, q2));
//        q1.setQuestionnaire(questionnaire);
//        q2.setQuestionnaire(questionnaire);

        QuestionnaireDto savedDto = questionnaireService.addQuestionnaire(newQuestionnaireRequest);

        assertNotNull(savedDto.id());
        assertEquals(title, savedDto.title());
        assertEquals(startDate, savedDto.startDate());
        assertEquals(endDate, savedDto.endDate());
        assertEquals(description, savedDto.description());
//        assertEquals(q1Text, savedDto.questions().getFirst().text());
//        assertEquals(q1Type.name(), savedDto.questions().getFirst().type());
//        assertEquals(q2Text, savedDto.questions().getLast().text());
//        assertEquals(q2Type.name(), savedDto.questions().getLast().type());
//        assertNotNull(savedDto.questions().getLast().answers());
//        assertEquals(0, savedDto.questions().getLast().answers().length);
//        assertEquals(q2a1, savedDto.questions().getLast().answers()[0]);
//        assertEquals(q2a2, savedDto.questions().getLast().answers()[1]);
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        String urlWithoutPrefix = postgreSQLContainer.getJdbcUrl().substring(18);
        registry.add("spring.datasource.driver-class-name", () -> "org.testcontainers.jdbc.ContainerDatabaseDriver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
        registry.add("spring.datasource.url", () -> urlWithoutPrefix);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.flyway.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.flyway.user", postgreSQLContainer::getUsername);
        registry.add("spring.flyway.password", postgreSQLContainer::getPassword);
    }

}
