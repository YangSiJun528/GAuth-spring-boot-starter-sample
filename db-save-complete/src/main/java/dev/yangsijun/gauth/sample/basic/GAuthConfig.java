package dev.yangsijun.gauth.sample.basic;

import dev.yangsijun.gauth.userinfo.GAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GAuthConfig {

    private final GAuthUserRepository gauthUserRepository;

    @Bean
    public GAuthUserService customGAuthUserService() {
        return new CustomGAuthUserService(gauthUserRepository);
    }
}
