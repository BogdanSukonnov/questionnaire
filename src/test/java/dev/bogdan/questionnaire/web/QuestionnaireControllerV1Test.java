package dev.bogdan.questionnaire.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bogdan.questionnaire.controller.QuestionnaireControllerV1;
import dev.bogdan.questionnaire.dto.QuestionnaireDto;
import dev.bogdan.questionnaire.security.AuthenticationService;
import dev.bogdan.questionnaire.security.WebSecurityConfiguration;
import dev.bogdan.questionnaire.service.QuestionnaireService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({QuestionnaireControllerV1.class, WebSecurityConfiguration.class, AuthenticationService.class})
@TestPropertySource(properties = {"api.key-header-name=api-key-header", "api.key=api-key-value"})
class QuestionnaireControllerV1Test {

    public static final String API_KEY_HEADER = "api-key-header";
    public static final String API_KEY_VALUE = "api-key-value";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private QuestionnaireService questionnaireService;

    @Test
    void shouldFailAnonymously() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/questionnaires?page=0&size=10"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnQuestionariesAuthorized() throws Exception {

        QuestionnaireDto q1 = new QuestionnaireDto(
                1L,
                3,
                "title1",
                LocalDateTime.of(2024, 5, 24, 13, 0, 0),
                LocalDateTime.of(2024, 6, 24, 13, 0, 0),
                null
        );

        QuestionnaireDto q2 = new QuestionnaireDto(
                2L,
                7,
                "title2",
                LocalDateTime.of(2024, 5, 24, 13, 0, 0),
                null,
                null
        );

        Page<QuestionnaireDto> expectedResponse = new PageImpl<>(List.of(q1, q2));

        when(questionnaireService.getAllQuestionnairesPaginated(0, 10)).thenReturn(expectedResponse);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        this.mockMvc
                .perform(get("/api/v1/questionnaires?page=0&size=10")
                        .header(API_KEY_HEADER, API_KEY_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(q1.id().intValue())))
                .andExpect(jsonPath("$.content[0].title", is(q1.title())))
                .andExpect(jsonPath("$.content[0].startDate", is(dateFormatter.format(q1.startDate()))))
                .andExpect(jsonPath("$.content[0].endDate", is(dateFormatter.format(q1.endDate()))))
                .andExpect(jsonPath("$.content[1].id", is(q2.id().intValue())))
                .andExpect(jsonPath("$.content[1].title", is(q2.title())))
        ;

    }

    @Test
    void shouldDeleteQuestionary() throws Exception {
        this.mockMvc
                .perform(delete("/api/v1/questionnaires/777")
                        .header(API_KEY_HEADER, API_KEY_VALUE)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

}
