package dev.bogdan.questionnaire.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bogdan.questionnaire.controller.ActiveQuestionnaireControllerV1;
import dev.bogdan.questionnaire.dto.QuestionaryDto;
import dev.bogdan.questionnaire.security.WebSecurityConfiguration;
import dev.bogdan.questionnaire.service.QuestionnaireService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({ActiveQuestionnaireControllerV1.class, WebSecurityConfiguration.class})
class ActiveQuestionnaireControllerV1Test {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionnaireService questionnaireService;

    @Test
    void shouldReturnActiveQuestionariesAnonymously() throws Exception {

        QuestionaryDto q1 = new QuestionaryDto(
                1L,
                3,
                "title1",
                LocalDateTime.of(2024, 5, 24, 13, 0, 0),
                null,
                null,
                null
        );

        QuestionaryDto q2 = new QuestionaryDto(
                2L,
                7,
                "title2",
                LocalDateTime.of(2024, 5, 24, 13, 0, 0),
                null,
                null,
                null
        );

        Page<QuestionaryDto> expectedPage = new PageImpl<>(List.of(q1, q2));

        when(questionnaireService.getActiveQuestionnairesPaginated(0, 2)).thenReturn(expectedPage);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        this.mockMvc
                .perform(get("/api/v1/active-questionnaires?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(q1.id().intValue())))
                .andExpect(jsonPath("$.content[0].title", is(q1.title())))
                .andExpect(jsonPath("$.content[0].startDate", is(dateFormatter.format(q1.startDate()))))
                .andExpect(jsonPath("$.content[1].id", is(q2.id().intValue())))
                .andExpect(jsonPath("$.content[1].title", is(q2.title())))
        ;

    }

}