package com.maxxxdj.audioconverter.web.cfg;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Key authentication filter!!!
 *
 * @author martin.miloshev
 */
@Slf4j
@Service
public class ApiKeyAuthFilter extends GenericFilterBean {

    private final AuthenticationService authenticationService;




    public ApiKeyAuthFilter(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

    }


    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
                         final FilterChain filterChain) {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            System.out.println(httpRequest.getServletPath());
            if(!httpRequest.getServletPath().contains("actuator")) {
                final Authentication authentication = authenticationService.getAuthentication(httpRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (Exception e) {
            final HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            writeExceptionToResponse(response, e);
        }
    }

    private void writeExceptionToResponse(final HttpServletResponse response, final Exception e) {
        try (final PrintWriter writer = response.getWriter()) {
            writer.write(e.getMessage());
            writer.flush();
        }
        catch (final IOException ex) {
            log.warn("Failed writing exception to response.");
        }
    }
}