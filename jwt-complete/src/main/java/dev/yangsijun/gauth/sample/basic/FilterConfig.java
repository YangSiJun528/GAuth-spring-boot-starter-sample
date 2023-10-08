package dev.yangsijun.gauth.sample.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    private final GAuthUserRepository gauthUserRepository;

    public FilterConfig(GAuthUserRepository gauthUserRepository) {
        this.gauthUserRepository = gauthUserRepository;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(gauthUserRepository);
    }
}
