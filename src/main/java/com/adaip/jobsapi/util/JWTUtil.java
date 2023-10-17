package com.adaip.jobsapi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class JWTUtil {

    @Value("${jwt.secret.key}")
    private String secret_key;
    public String generateToken(Long userId, String username) {
        String tokenId= UUID.randomUUID().toString();

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);

        return Jwts.builder()
                .setId(tokenId)
                .setClaims(claims)
                .setIssuer("JOBS_API")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 7 * TimeUnit.HOURS.toMillis(1)))
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(secret_key.getBytes()))
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encode(secret_key.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }
}
