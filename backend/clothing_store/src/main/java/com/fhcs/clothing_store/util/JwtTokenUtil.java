package com.fhcs.clothing_store.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fhcs.clothing_store.security.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {

    @Value("${api.secret.key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public String generateAccessToken(UserDetailsImpl userDetails) {

        Map<String, Object> claims = new HashMap<>();

        List<String> roles = userDetails.getUser().getRoles().stream()
                .map(role -> role.getRoleName())
                .collect(Collectors.toList());

        claims.put("roles", roles);

        String token = Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getJwtSecretKey(secretKey))
                .compact();

        return token;
    }

    public String generateRefreshToken(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();

        List<String> roles = userDetails.getUser().getRoles().stream()
                .map(role -> role.getRoleName())
                .collect(Collectors.toList());

        claims.put("roles", roles);

        String token = Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(getJwtSecretKey(secretKey))
                .compact();

        return token;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getJwtSecretKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private SecretKey getJwtSecretKey(String key) {

        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isAccessTokenExpired(token));

    }

    public boolean validateRefreshToken(String token) {

        if (isRefreshTokenExpired(token)) {
            return false;
        }

        return true;

    }

    private boolean isRefreshTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private boolean isAccessTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public long getAccessTokenExpiration(String accessToken) {
        long expiresIn = extractExpiration(accessToken).getTime();
        return expiresIn;
    }

}