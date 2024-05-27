package dev.bogdan.questionnaire.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers(
                        new AntPathRequestMatcher("/api/v1/user-questionnaires/**"),
                        new AntPathRequestMatcher("/api/v1/active-questionnaires/**")
                )
                        .permitAll()
                .anyRequest()
                        .authenticated()
        ).build();
    }
}
