package code_sync_team.blog.user;

import code_sync_team.blog.user.dto.UserJoinRequest;
import code_sync_team.blog.user.dto.UserLoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@Tag(name = "User", description = "유저 관련 API")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @Operation(
            summary = "회원가입",
            description = "회원가입을 합니다."
    )
    @ApiResponse(
            responseCode = "201",
            description = "회원가입에 성공하였습니다."
    )
    @PostMapping("/join")
    public ResponseEntity<User> create(@RequestBody UserJoinRequest dto) {
        return new ResponseEntity<>(userService.create(dto), HttpStatus.CREATED);
    }


    @Operation(
            summary = "로그인",
            description = "로그인을 합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "로그인에 성공하였습니다."
    )
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserLoginRequest dto) {
        return new ResponseEntity<>(userService.login(dto), HttpStatus.OK);
    }
}
