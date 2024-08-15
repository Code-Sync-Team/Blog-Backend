package code_sync_team.blog.user;

import code_sync_team.blog.global.auth.JwtProvider;
import code_sync_team.blog.user.dto.UserJoinRequest;
import code_sync_team.blog.user.dto.UserLoginRequest;
import code_sync_team.blog.user.exception.UserErrorCode;
import code_sync_team.blog.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public User create(UserJoinRequest dto) {

        if (userRepository.existsByEmail(dto.getEmail()))
            throw new UserException(UserErrorCode.USERNAME_ALREADY_EXISTS);

        return userRepository.save(
                User.builder()
                        .email(dto.getEmail())
                        .password(dto.getPassword())
                        .name(dto.getName())
                        .nickName(dto.getNickname())
                        .build()
        );
    }

    public String login(UserLoginRequest dto) {

        User user = userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword())
                .orElseThrow(() -> new UserException(UserErrorCode.MEMBER_NOT_FOUND));

        return jwtProvider.createToken(user);
    }


}
