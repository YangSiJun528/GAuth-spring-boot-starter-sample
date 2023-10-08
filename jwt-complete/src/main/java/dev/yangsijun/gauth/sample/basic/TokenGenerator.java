package dev.yangsijun.gauth.sample.basic;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static dev.yangsijun.gauth.sample.basic.JwtProperties.*;

public class TokenGenerator {

    public static TokenResponse generateToken(long userId) {
        return new TokenResponse(
                generateAccessToken(userId)
        );
    }

    private static String generateAccessToken(long userId) {
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(ACCESS_EXP, ChronoUnit.SECONDS);
        return Jwts.builder()
                .signWith(ACCESS_SECRET, SignatureAlgorithm.HS256)
                .setSubject(Long.toString(userId))
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .compact();
    }
}
