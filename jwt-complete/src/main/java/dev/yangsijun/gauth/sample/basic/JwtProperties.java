package dev.yangsijun.gauth.sample.basic;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtProperties {
    public static final String HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final Integer ACCESS_EXP = 2592000; // 3 day
    public static final String ACCESS_SECRET_VALUE = "qwertyuiopasdfghjklzxcvbnm1234567890qwertyuiopasdfghjklzxcvbnm1234567890";
    public static final SecretKey ACCESS_SECRET = Keys.hmacShaKeyFor(ACCESS_SECRET_VALUE.getBytes(StandardCharsets.UTF_8));
}
