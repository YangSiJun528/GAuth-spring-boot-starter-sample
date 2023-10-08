package dev.yangsijun.gauth.sample.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.yangsijun.gauth.authentication.GAuthAuthenticationToken;
import dev.yangsijun.gauth.core.user.GAuthUser;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        GAuthAuthenticationToken gauthAuthToken = (GAuthAuthenticationToken) authentication;
        GAuthUser user = gauthAuthToken.getPrincipal();
        String key = authentication.getName();
        long userId = user.getAttribute(key);

        TokenResponse token = TokenGenerator.generateToken(userId);
        sendTokenResponse(response, token);
    }

    private void sendTokenResponse(HttpServletResponse response, TokenResponse token) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        response.getWriter().write(objectMapper.writeValueAsString(token));
    }
}
