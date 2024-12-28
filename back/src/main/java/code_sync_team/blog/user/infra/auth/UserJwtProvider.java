package code_sync_team.blog.user.infra.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class UserJwtProvider {
    private String secret = "this-is-secret-key-value-at-least-128-bytes";
    private static int ACCESS_TOKEN_EXPIRES_TIME = 60 * 60;
    private static int REFRESH_TOKEN_EXPIRES_TIME = 60 * 60 * 24;

    private Key key;

    @PostConstruct
    private void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(Long id) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        claims.put("token_type", "access_token");

        return createToken(claims, ACCESS_TOKEN_EXPIRES_TIME);
    }

    private String createToken(Claims claims, int expiresTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt())
                .setExpiration(expiredAt(expiresTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Date issuedAt() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.atZone(ZoneId.systemDefault())
                .toInstant());

    }

    private Date expiredAt(int expiresTime) {
        LocalDateTime now = LocalDateTime.now();

        return Date.from(now.plusDays(expiresTime)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
