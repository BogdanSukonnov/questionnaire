package dev.bogdan.questionnaire.web;

import dev.bogdan.questionnaire.controller.ActiveQuestionnaireControllerV1;
import dev.bogdan.questionnaire.dto.QuestionnaireDto;
import dev.bogdan.questionnaire.security.AuthenticationService;
import dev.bogdan.questionnaire.security.WebSecurityConfiguration;
import dev.bogdan.questionnaire.service.ActiveQuestionnaireService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({ActiveQuestionnaireControllerV1.class, WebSecurityConfiguration.class, AuthenticationService.class})
class ActiveQuestionnaireControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActiveQuestionnaireService activeQuestionnaireService;

    @Test
    void shouldReturnActiveQuestionariesAnonymously() throws Exception {

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

        List<QuestionnaireDto> expectedResponse = List.of(q1, q2);

        when(activeQuestionnaireService.getActiveQuestionnaires(777L)).thenReturn(expectedResponse);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        this.mockMvc
                .perform(get("/api/v1/active-questionnaires?user_id=777"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(q1.id().intValue())))
                .andExpect(jsonPath("$[0].title", is(q1.title())))
                .andExpect(jsonPath("$[0].startDate", is(dateFormatter.format(q1.startDate()))))
                .andExpect(jsonPath("$[0].endDate", is(dateFormatter.format(q1.endDate()))))
                .andExpect(jsonPath("$[1].id", is(q2.id().intValue())))
                .andExpect(jsonPath("$[1].title", is(q2.title())))
        ;

    }

}