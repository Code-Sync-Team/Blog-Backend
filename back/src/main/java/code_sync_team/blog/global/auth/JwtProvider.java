package code_sync_team.blog.global.auth;

import code_sync_team.blog.user.User;
import code_sync_team.blog.user.exception.UserErrorCode;
import code_sync_team.blog.user.exception.UserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    private String issuer = "code_sync_team";

    private Long accessExpiration = 60 * 60 * 1000L;

    private final SecretKey secretKey;

    public JwtProvider() {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode("c3ByaW5nYm9vdC1qd3QtdHV0b3JpYWwtc3ByaW5nYm9vdC1qd3QtdHV0b3JpYWwtc3ByaW5nYm9vdC1qd3QtdHV0b3JpYWwK"));
    }

    public String createToken(User user) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String userJson = objectMapper.writeValueAsString(user);

            String token = Jwts.builder()
                    .claim("user", userJson)
                    .issuer(issuer)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                    .signWith(secretKey, Jwts.SIG.HS512)
                    .compact();

            return token;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UserException(UserErrorCode.DEFAULT);
        }
    }


    public UserDetail parseToken(String token) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Claims claims = Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token).getBody();

            String userJson = claims.get("user").toString();

            User user = objectMapper.readValue(userJson, User.class);
            return new UserDetail(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UserException(UserErrorCode.DEFAULT);
        }
    }

    public boolean verifyToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        Claims claims = Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getBody();

        return claims.getExpiration().after(new Date());
    }
}
