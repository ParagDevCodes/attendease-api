package com.attendease.attendease_api.utils;

import com.attendease.attendease_api.model.Users;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Value("${jwt.token.expiration.time}")
    private long expirationTime;

    private Key getSigninKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    public String generateToken(Users users){
        return Jwts.builder()
                .setSubject(users.getId())
                .claim("role", users.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }

    public String extractUserId(String token){
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public static void main(String[] args) {
//        Users users = Users.builder()
//                .role(Utils.Role.ADMIN)
//                .fullName("System Admin")
//                .build();
//        String token = generateToken(users);
//        System.out.println("generated token -> " +token);
//
//        System.out.println("valid token -> " +validateToken(token));
//
//        System.out.println("extracted user id -> " +extractUserId(token));
    }
}
