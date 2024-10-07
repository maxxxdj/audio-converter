package com.maxxxdj.audioconverter.web.cfg;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${custom.web.security.token_name}")
    private String AUTH_TOKEN_HEADER_NAME;

    @Value("${custom.web.security.token_value}")
    private String token;

    public Authentication getAuthentication(final HttpServletRequest request) {
        final String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        final String requestIp = request.getRemoteAddr();
        final String endpoint = request.getServletPath();

        if (ObjectUtils.isEmpty(apiKey) || !apiKey.equals(token)) {
            log.warn("Access denied. Unauthorized attempt detected from {} for path {}!",
                    requestIp, endpoint);
            throw new BadCredentialsException("Invalid API key");
        }

        return new ApiKeyAuthentication(AuthorityUtils.NO_AUTHORITIES, apiKey);
    }
}
