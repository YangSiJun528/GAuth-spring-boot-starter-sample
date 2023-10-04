package dev.yangsijun.gauth.sample.basic;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final GAuthUserRepository gauthUserRepository;

    public Controller(GAuthUserRepository gauthUserRepository) {
        this.gauthUserRepository = gauthUserRepository;
    }

    @GetMapping("/role/student")
    public String student() {
        return "hi student!";
    }

    @GetMapping("/role/teacher")
    public String teacher() {
        return "hi teacher!";
    }

    @GetMapping("/auth/me")
    public Object info() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal();
    }

    @GetMapping("/user/me")
    public GAuthUserEntity userMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        GAuthUserEntity entity = gauthUserRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("entity를 찾을 수 없습니다."));
        return entity;
    }
}
