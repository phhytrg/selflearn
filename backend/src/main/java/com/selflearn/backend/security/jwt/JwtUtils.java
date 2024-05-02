package com.selflearn.backend.security.jwt;

import com.selflearn.backend.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtHandler;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${selflearn.app.jwtSecret}")
    private String jwtSecret;

    @Value("${selflearn.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .subject(String.valueOf((userPrincipal.getId())))
                .claims(getClaims(userPrincipal))
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime()) + jwtExpirationMs))
                .signWith(getSecretKey())
                .compact();
    }

    private Map<String, Object> getClaims(UserDetails userPrincipal){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userPrincipal.getUsername());
        claims.put("roles", userPrincipal.getAuthorities());
        return claims;
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public UUID getSubjectFromToken(String token) {
        JwtParser parser = Jwts.parser().verifyWith(getSecretKey()).build();
        return UUID.fromString(((Claims) parser.parse(token).getPayload()).getSubject());
    }

    public boolean validateJwtToken(String authToken) {
        JwtParser parser = Jwts.parser().verifyWith(getSecretKey()).build();
        return parser.isSigned(authToken);
    }
}
