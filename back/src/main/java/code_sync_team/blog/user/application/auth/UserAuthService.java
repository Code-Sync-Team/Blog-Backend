package code_sync_team.blog.user.application.auth;

import code_sync_team.blog.user.domain.AuthType;
import code_sync_team.blog.user.domain.User;
import code_sync_team.blog.user.domain.UserRepository;
import code_sync_team.blog.user.infra.auth.UserJwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;
    private final UserJwtProvider userJwtProvider;

    public String getAccessToken(String email, AuthType authType) {
        User user = findOrCreateUserByEmail(email, authType);
        return userJwtProvider.createToken(user.getId());
    }

    private User findOrCreateUserByEmail(String email, AuthType authType) {
        User user = userRepository.findByEmailAndAuthType(email, authType).orElse(createOAuthUserByEmail(email, authType));
        return user;
    }

    private User createOAuthUserByEmail(String email, AuthType authType) {
        return User.createByEmailWithAuthType(email, authType);
    }
}
