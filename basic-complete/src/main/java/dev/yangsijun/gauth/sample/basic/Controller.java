package dev.yangsijun.gauth.sample.basic;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
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
}
