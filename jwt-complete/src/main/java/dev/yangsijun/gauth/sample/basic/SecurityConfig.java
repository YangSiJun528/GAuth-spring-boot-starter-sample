package dev.yangsijun.gauth.sample.basic;

import dev.yangsijun.gauth.configurer.GAuthLoginConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final GAuthLoginConfigurer<HttpSecurity> gauth;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(headersConfigurer -> headersConfigurer.frameOptions().sameOrigin())
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request
                        .antMatchers(
                        "/gauth/authorization",
                        "/login/gauth/code"
                        ).permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .antMatchers("/auth/me").authenticated()
                        .antMatchers("/user/me").authenticated()
                        .antMatchers("/role/student").hasAuthority("ROLE_STUDENT")
                        .antMatchers("/role/teacher").hasAuthority("ROLE_TEACHER")
                        .anyRequest().denyAll()
                )
                .logout(withDefaults())
                .apply(gauth
                        .successHandler(new CustomAuthenticationSuccessHandler()));
        return http.build();
    }
}
