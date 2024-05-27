package dev.bogdan.questionnaire.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    @Value("${api.key-header-name}")
    private String AUTH_TOKEN_HEADER_NAME;

    @Value("${api.key}")
    private String AUTH_TOKEN;

    public Authentication getAuthentication(HttpServletRequest request) {

        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, List.of(new SimpleGrantedAuthority("ADMIN")));
    }
}
