package com.wigo.server.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
        try {
            // can parser be a singleton?
            return UUID.fromString(Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody().getSubject());
        } catch (IllegalArgumentException e) {
            throw new JwtException(e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new JwtLogic(new JwtKeyProvider().jwtKey()).getJwtToken(UUID.fromString("f908854b-93f5-48bc-9213-7abcb1169d48")));
    }

}
