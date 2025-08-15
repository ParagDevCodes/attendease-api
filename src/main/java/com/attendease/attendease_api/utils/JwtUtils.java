package com.attendease.attendease_api.utils;

import com.attendease.attendease_api.constant.AppConstant;
import com.attendease.attendease_api.model.Users;
import io.jsonwebtoken.*;
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

    public record TokenValidationResult(AppConstant.TokenStatus status, Claims claims) {}

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

    public TokenValidationResult validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigninKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return new TokenValidationResult(AppConstant.TokenStatus.VALID, claims);

        } catch (ExpiredJwtException e) {
            return new TokenValidationResult(AppConstant.TokenStatus.EXPIRED, e.getClaims());
        } catch (MalformedJwtException e) {
            return new TokenValidationResult(AppConstant.TokenStatus.MALFORMED, null);
        } catch (UnsupportedJwtException e) {
            return new TokenValidationResult(AppConstant.TokenStatus.UNSUPPORTED, null);
        } catch (SignatureException e) {
            return new TokenValidationResult(AppConstant.TokenStatus.INVALID_SIGNATURE, null);
        } catch (JwtException e) {
            return new TokenValidationResult(AppConstant.TokenStatus.MALFORMED, null);
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
