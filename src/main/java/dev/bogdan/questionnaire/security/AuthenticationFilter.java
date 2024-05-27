package dev.bogdan.questionnaire.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final Set<String> publicEndpoints;
    private final AuthenticationService authenticationService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception exp) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpResponse.getWriter();
            writer.print(exp.getMessage());
            writer.flush();
            writer.close();
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return publicEndpoints.contains(request.getRequestURI());
    }
}
