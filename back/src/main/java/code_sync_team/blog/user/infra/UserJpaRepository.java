package code_sync_team.blog.user.infra;


import code_sync_team.blog.user.domain.AuthType;
import code_sync_team.blog.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndAuthType(String email, AuthType authType);
}
