package code_sync_team.blog.user;

import code_sync_team.blog.user.dto.UserJoinRequest;
import code_sync_team.blog.user.dto.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<User> create(@RequestBody UserJoinRequest dto) {
        return new ResponseEntity<>(userService.create(dto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserLoginRequest dto) {
        return new ResponseEntity<>(userService.login(dto), HttpStatus.OK);
    }
}
