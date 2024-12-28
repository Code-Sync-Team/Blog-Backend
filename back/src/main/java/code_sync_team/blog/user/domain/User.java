package code_sync_team.blog.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    public Long getId() {
        return id;
    }

    public static User createByEmailWithAuthType(String email, AuthType authType) {
        return User.builder()
                .email(email)
                .authType(authType)
                .build();
    }
}
