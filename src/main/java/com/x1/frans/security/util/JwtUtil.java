package com.x1.frans.security.util;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.security.config.properties.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

public class JwtUtil {

    private final TokenProperties tokenProperties;
    private final SecretKey secretKey;

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

        return Jwts.builder()
                .setSubject(userCode)
                .setExpiration(new Date(System.currentTimeMillis() + tokenProperties.getRefreshExpirationTime()))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
