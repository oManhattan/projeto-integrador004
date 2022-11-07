package com.pi.logic.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil implements Serializable {

    private static final long serialVersionUID = 234234523523L;
    private static final String SECRET_KEY = "secretKey123";
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Long getIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Long id = ((Number) claims.get("user_id")).longValue();
        return id; 
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @SuppressWarnings("unchecked")
    public String getUserAuthorityFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Map<String, String> authority = (Map<String, String>) claims.get("role");
        return authority.get("authority");
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(Long id, String email, SimpleGrantedAuthority authority) {
        Map<String, Object> claims = Map.of("user_id", id, "role", authority);
        return doGenerateToken(claims, email);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String formatToken(String token) throws Exception {

        String formattedToken = token.substring(7);

        if (token.isEmpty()) {
            throw new Exception("Token inválido.");
        }

        if (isTokenExpired(formattedToken)) {
            throw new Exception("Token expirado.");
        }

        return formattedToken;
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
