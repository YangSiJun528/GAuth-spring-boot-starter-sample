package dev.yangsijun.gauth.sample.basic;

import dev.yangsijun.gauth.configurer.GAuthLoginConfigurer;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    private final GAuthLoginConfigurer<HttpSecurity> gauth;

    public SecurityConfig(GAuthLoginConfigurer<HttpSecurity> gAuthLoginConfigurer) {
        this.gauth = gAuthLoginConfigurer;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(headersConfigurer -> headersConfigurer.frameOptions().sameOrigin())
                .authorizeHttpRequests(request -> request
                        .antMatchers(
                        "/gauth/authorization",
                        "/login/gauth/code"
                        ).permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .antMatchers("/auth/me").authenticated()
                        .antMatchers("/user/me").authenticated()
                        .antMatchers("/role/student").hasAuthority("GAUTH_ROLE_STUDENT")
                        .antMatchers("/role/teacher").hasAuthority("GAUTH_ROLE_TEACHER")
                        .anyRequest().denyAll()
                )
                .logout(withDefaults())
                .apply(gauth);
        return http.build();
    }
}
