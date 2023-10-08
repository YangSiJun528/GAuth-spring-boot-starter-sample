package dev.yangsijun.gauth.sample.basic;

import dev.yangsijun.gauth.core.user.GAuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class CustomGAuthUser implements GAuthUser {

    private final Long id;
    private final String role;

    public CustomGAuthUser(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return getAttribute(getName());
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(getName(), id);
        return attributes;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public String getName() {
        return "id";
    }
}
