package dev.yangsijun.gauth.sample.basic;

import dev.yangsijun.gauth.userinfo.GAuthUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GAuthConfig {

    private final GAuthUserRepository gauthUserRepository;

    public GAuthConfig(GAuthUserRepository gauthUserRepository) {
        this.gauthUserRepository = gauthUserRepository;
    }

    @Bean
    public GAuthUserService customGAuthUserService() {
        return new CustomGAuthUserService(gauthUserRepository);
    }
}
