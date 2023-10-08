package dev.yangsijun.gauth.sample.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
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

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final GAuthUserRepository gauthUserRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String jwtTokenHeader = request.getHeader(JwtProperties.HEADER);

        if (shouldSkipAuthentication(jwtTokenHeader)) {
            super.doFilter(request, response, filterChain);
            return;
        }

        String accessToken = extractAccessToken(jwtTokenHeader);
        Long userId = getUserIdFromToken(accessToken);
        GAuthUserEntity gauthUser = getGAuthUserById(userId);

        Authentication authentication = createAuthenticationToken(gauthUser);

        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.doFilter(request, response, filterChain);
    }

    private boolean shouldSkipAuthentication(String jwtTokenHeader) {
        return jwtTokenHeader == null || !jwtTokenHeader.startsWith(JwtProperties.TOKEN_PREFIX);
    }

    private String extractAccessToken(String jwtTokenHeader) {
        return jwtTokenHeader.replace(JwtProperties.TOKEN_PREFIX, "");
    }

    private Long getUserIdFromToken(String accessToken) {
        String userId = TokenParser.getTokenSubjectOrNull(accessToken);
        if (userId == null) {
            throw new BadCredentialsException("만료되거나 유효하지 않은 JWT");
        }
        return Long.valueOf(userId);
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
