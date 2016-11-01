package com.wigo.server.controllers;

import com.google.common.io.Resources;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;

@Component
public class JwtKeyProvider {
    @Bean
    public Key jwtKey() throws IOException {
//        Files.write(Paths.get("src/main/resources/jwt_key"), MacProvider.generateKey(SignatureAlgorithm.HS512).getEncoded());
        return new SecretKeySpec(Resources.toByteArray(LoginController.class.getResource("/jwt_key")), "HmacSHA512");
    }
}
