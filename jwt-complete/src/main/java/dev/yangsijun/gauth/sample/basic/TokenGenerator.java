package dev.yangsijun.gauth.sample.basic;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.util.Date;

import static dev.yangsijun.gauth.sample.basic.JwtProperties.*;

public class TokenGenerator {

    public static TokenResponse generateToken(long userId) {
        return new TokenResponse(
                generateAccessToken(userId)
        );
    }

    private static String generateAccessToken(long userId) {
        return Jwts.builder()
                .signWith(ACCESS_SECRET, SignatureAlgorithm.HS256)
                .setSubject(Long.toString(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXP * 1000))
                .compact();
    }
}
