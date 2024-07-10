package com.project.expensetracker.util;

import com.project.expensetracker.exception.JwtGenerationException;
import com.project.expensetracker.exception.JwtParsingException;
import com.project.expensetracker.exception.JwtValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class Jwtutils {

    private static final Logger logger = LoggerFactory.getLogger(Jwtutils.class);
    private static final String secret = "secretkey";

    public String generateJwt(Long userId, String username) {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", userId);
            claims.put("username",username);
            int jwtExpirationMs = 5 * 60 * 60 * 1000; // 5 hours in milliseconds

            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        } catch (Exception e) {
            logger.error("Failed to generate JWT token: {}", e.getMessage());
            throw new JwtGenerationException("Failed to generate JWT token", e);
        }
    }
    public Long extractUserId(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.get("userId", Long.class);
        } catch (Exception e) {
            logger.error("Failed to extract user ID from token: {}", e.getMessage());
            throw new JwtParsingException("Failed to extract user ID from token", e);
        }
    }
    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            logger.error("Failed to extract username from token: {}", e.getMessage());
            throw new JwtParsingException("Failed to extract username from token", e);
        }
    }

    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            logger.error("Failed to extract expiration from token: {}", e.getMessage());
            throw new JwtParsingException("Failed to extract expiration from token", e);
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            logger.error("Failed to extract claim from token: {}", e.getMessage());
            throw new JwtParsingException("Failed to extract claim from token", e);
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            logger.error("Failed to parse JWT token: {}", e.getMessage());
            throw new JwtParsingException("Failed to parse JWT token", e);
        }
    }

    public Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            logger.error("Failed to check token expiration: {}", e.getMessage());
            throw new JwtParsingException("Failed to check token expiration", e);
        }
    }

    public Boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (Exception e) {
            logger.error("Failed to validate JWT token: {}", e.getMessage());
            throw new JwtValidationException("Failed to validate JWT token", e);
        }
    }
}
