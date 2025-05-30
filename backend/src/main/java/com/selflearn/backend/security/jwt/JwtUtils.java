package com.selflearn.backend.security.jwt;

import com.selflearn.backend.security.models.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
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

    private Map<String, Object> getClaims(UserDetailsImpl userPrincipal) {
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

    public String getEmailFromToken(String token) throws BadCredentialsException {
        JwtParser parser = Jwts.parser().verifyWith(getSecretKey()).build();
        try {
            Map<String, ?> claims = parser.parseSignedClaims(token).getPayload();
            return (String) claims.get("email");
        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException("Token expired");
        } catch (SignatureException e) {
            throw new BadCredentialsException("Signature not match");
        }
    }

    public boolean validateJwtToken(String authToken) {
        JwtParser parser = Jwts.parser().verifyWith(getSecretKey()).build();
        return parser.isSigned(authToken);
    }
}
