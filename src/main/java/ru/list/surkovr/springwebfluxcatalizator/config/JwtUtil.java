package ru.list.surkovr.springwebfluxcatalizator.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.list.surkovr.springwebfluxcatalizator.domain.User;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expirationTimeS;

    public String extractUsername(String authToken) {
        // Берем ключ, передаем в билдер, который построил парсер, куда передаем токен, с которого получаем контент
        // в расшифрованном виде и вытаскиваем сабджект - юзер, который там лежит
        return getClaimsFromToken(authToken).getSubject();
    }

    public boolean validate(String authToken) {
        return getClaimsFromToken(authToken)
                .getExpiration().after(new Date());
    }

    public Claims getClaimsFromToken(String authToken) {
        final String key = Base64.getEncoder().encodeToString(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authToken)
                .getBody();
    }

    public String generateToken(User user) {
        final HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", List.of(user.getRole()));

        final long expirationSeconds = Long.parseLong(expirationTimeS);
        final Date creationDate = new Date();
        final Date expirationDate = new Date(creationDate.getTime() + expirationSeconds * 1000);

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(creationDate)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}
