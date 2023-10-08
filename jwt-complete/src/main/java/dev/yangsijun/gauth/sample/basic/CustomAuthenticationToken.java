package dev.yangsijun.gauth.sample.basic;

import dev.yangsijun.gauth.core.GAuthPluginVersion;
import dev.yangsijun.gauth.core.user.GAuthUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = GAuthPluginVersion.SERIAL_VERSION_UID;
    private final GAuthUser principal;

    public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities, GAuthUser principal) {
        super(authorities);
        this.principal = principal;
    }

    @Override
    public Long getCredentials() {
        return principal.getAttribute(getName());
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
