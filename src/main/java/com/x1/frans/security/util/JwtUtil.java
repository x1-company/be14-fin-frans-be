package com.x1.frans.security.util;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.security.config.properties.TokenProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final TokenProperties tokenProperties;
    private final SecretKey secretKey;

    @Autowired
    public JwtUtil(final TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
        this.secretKey = createSecretKey();
    }

    private SecretKey createSecretKey() {
        return Keys.hmacShaKeyFor(tokenProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(CustomUserDetails userDetails) {

        Claims claims = Jwts.claims();
        claims.put("userCode", userDetails.getUsername());
        claims.put("userId", userDetails.getUserId());
        claims.put("userProfileUrl", userDetails.getProfileUrl());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        claims.put("roles", roles);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenProperties.getAccessExpirationTime());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(String userCode) {

        Claims claims = Jwts.claims();
        claims.put("userCode", userCode);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + tokenProperties.getRefreshExpirationTime()))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String resolveAccessToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
    }

    public String getUserCode(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.get("userCode", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            validateToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public long getRemainingExpiration(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date expiration = claims.getExpiration();
        long now = System.currentTimeMillis();

        return expiration.getTime() - now;
    }
}
