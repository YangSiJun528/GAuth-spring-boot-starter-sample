package dev.yangsijun.gauth.sample.simple;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import dev.yangsijun.gauth.configurer.GAuthLoginConfigurer;

@Configuration
public class SecurityConfig {

    private final GAuthLoginConfigurer gauth;

    public SecurityConfig(GAuthLoginConfigurer gAuthLoginConfigurer) {
        this.gauth = gAuthLoginConfigurer;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(request -> request
                        // GAuth 인증에 사용되는 주소는 permit되어야 합니다.
                        .requestMatchers("/to-gauth-login-page",
                                "/login/gauth/code").permitAll()
                        .requestMatchers("/auth/**").authenticated()
                        .requestMatchers("/role/student").hasAuthority("ROLE_STUDENT")
                        .requestMatchers("/role/teacher").hasAuthority("ROLE_TEACHER")
                        .anyRequest().permitAll()
                )
                .logout(withDefaults())
                .apply(gauth
                        .loginPageUrl("/to-gauth-login-page")
                        .loginProcessingUrl("/login/code/gauth")
                        .successHandler(
                                new SimpleUrlAuthenticationSuccessHandler("/success"))
                        .failureHandler(
                                new SimpleUrlAuthenticationFailureHandler("/failure")));
        return http.build();
    }

}
