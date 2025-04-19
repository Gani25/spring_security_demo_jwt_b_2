package com.sprk.spring_security_demo.service;

import com.sprk.spring_security_demo.model.AuthRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    public String generateToken(AuthRequest authRequest) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(authRequest.getUsername(), claims);

    }

    private String createToken(String userName, Map<String, Object> claims) {

        System.out.println(SECRET);
        return Jwts.builder()
                .header().add(Map.of("alg", "HS256", "typ",
                        "JWT"))
                .and()
                .claims(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1)) // 1 minute
                .signWith(getSignInKey())
                .compact();
        /*
         * 1000 ms * 60 s * 15 min
         */
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
