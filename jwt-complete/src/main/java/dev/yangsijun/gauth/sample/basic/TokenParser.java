package dev.yangsijun.gauth.sample.basic;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;

public class TokenParser {

    public static String getTokenSubjectOrNull(String token) {
        try {
            return getTokenBody(token, JwtProperties.ACCESS_SECRET).getSubject();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    private static Claims getTokenBody(String token, Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
