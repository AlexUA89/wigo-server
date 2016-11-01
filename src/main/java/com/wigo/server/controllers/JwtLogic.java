package com.wigo.server.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtLogic {
    private final Key jwtKey;

    @Autowired
    public JwtLogic(Key jwtKey) {
        this.jwtKey = jwtKey;
    }

    public String getJwtToken(UUID userId) {
        Date tim = new Date();
        return Jwts.builder()
                .setIssuer("wigo.com")
                .setSubject(userId.toString())
                .setIssuedAt(tim)
                .setExpiration(new Date(tim.getTime() + 30 * 24 * 3600 * 1000L))
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .compact();
    }

    public UUID parseJwtToken(String token) {
        // can parser be a singleton?
        return UUID.fromString(Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody().getSubject());
    }
}
