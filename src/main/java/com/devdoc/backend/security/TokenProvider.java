package com.devdoc.backend.security;

import com.devdoc.backend.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {
    private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private Key secretKey;

    public TokenProvider() {
    }

    @PostConstruct
    public void init() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String create(UserEntity userEntity) {
        Date expiryDate = Date.from(Instant.now().plus(1L, ChronoUnit.DAYS));
        return Jwts.builder().signWith(this.secretKey).setSubject(userEntity.getId()).setIssuer("demo app").setIssuedAt(new Date()).setExpiration(expiryDate).compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = (Claims)Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}