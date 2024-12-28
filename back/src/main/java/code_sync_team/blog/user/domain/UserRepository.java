package code_sync_team.blog.user.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<User> findByEmailAndAuthType(String email, AuthType authType);
    User save(User usere);
}
