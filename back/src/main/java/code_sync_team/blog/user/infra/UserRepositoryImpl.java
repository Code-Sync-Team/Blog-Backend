package code_sync_team.blog.user.infra;

import code_sync_team.blog.user.domain.AuthType;
import code_sync_team.blog.user.domain.User;
import code_sync_team.blog.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmailAndAuthType(String email, AuthType authType) {
        return userJpaRepository.findByEmailAndAuthType(email, authType);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
}
