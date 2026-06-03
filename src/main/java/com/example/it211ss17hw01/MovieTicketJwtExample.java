package com.example.it211ss17hw01;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MovieTicketJwtExample {

    public static void main(String[] args) {
        // (1) Tạo ra Secret Key (dùng chung cho cả tạo và xác minh)
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // (2) Định nghĩa claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 123L);
        claims.put("roles", "USER");

        // (3) Thiết lập thời gian phát hành và hết hạn
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600 * 1000); // 1 giờ

        // (4) Xây dựng và ký JWT
        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setSubject("user@movieticket.com")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("Generated JWT: " + jwtToken);

        try {
            // (5) Xác minh JWT bằng cùng Secret Key
            var parsedClaims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            // (6) Trích xuất thông tin từ payload
            String subject = parsedClaims.getSubject();
            Long userId = parsedClaims.get("userId", Long.class);
            String roles = parsedClaims.get("roles", String.class);

            System.out.println("JWT is valid and verified!");
            System.out.println("Subject: " + subject);
            System.out.println("User ID: " + userId);
            System.out.println("Roles: " + roles);
        } catch (Exception e) {
            System.err.println("Invalid JWT: " + e.getMessage());
        }
    }
}
