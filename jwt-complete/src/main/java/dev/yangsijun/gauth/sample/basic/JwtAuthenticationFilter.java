package dev.yangsijun.gauth.sample.basic;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final GAuthUserRepository gauthUserRepository;

    public JwtAuthenticationFilter(GAuthUserRepository gauthUserRepository) {
        this.gauthUserRepository = gauthUserRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String accessTokenHeader = request.getHeader(JwtProperties.HEADER);

        if (shouldSkipAuthentication(accessTokenHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = extractAccessToken(accessTokenHeader);
        Long userId = getUserIdFromToken(accessToken);
        GAuthUserEntity gauthUser = getGAuthUserById(userId);

        Authentication authentication = createAuthenticationToken(gauthUser);

        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private boolean shouldSkipAuthentication(String accessTokenHeader) {
        return accessTokenHeader == null || !accessTokenHeader.startsWith(JwtProperties.TOKEN_PREFIX);
    }

    private String extractAccessToken(String accessTokenHeader) {
        return accessTokenHeader.replace(JwtProperties.TOKEN_PREFIX, "");
    }

    private Long getUserIdFromToken(String accessToken) {
        return Long.valueOf(TokenParser.getTokenSubject(accessToken));
    }

    private GAuthUserEntity getGAuthUserById(Long userId) {
        return gauthUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("요청한 사용자 ID와 일치하는 사용자를 찾을 수 없습니다"));
    }

    private Authentication createAuthenticationToken(GAuthUserEntity gauthUser) {
        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(gauthUser.getRole()));
        CustomGAuthUser customUser = new CustomGAuthUser(gauthUser.getId(), gauthUser.getRole());

        return new CustomAuthenticationToken(authorities, customUser);
    }
}
