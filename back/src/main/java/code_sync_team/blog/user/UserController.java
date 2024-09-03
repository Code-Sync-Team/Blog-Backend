package code_sync_team.blog.user;

import code_sync_team.blog.global.auth.LoginUser;
import code_sync_team.blog.global.auth.UserContextHolder;
import code_sync_team.blog.global.auth.UserDetail;
import code_sync_team.blog.user.dto.*;
import code_sync_team.blog.user.exception.UserErrorCode;
import code_sync_team.blog.user.exception.UserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@Tag(name = "User", description = "유저 관련 API")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
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
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest dto) {
        return new ResponseEntity<>(userService.login(dto), HttpStatus.OK);
    }


    @Operation(
            summary = "이메일 중복 확인",
            description = "이메일 중복을 확인합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "요청에 성공하였습니다."
    )
    @PostMapping("/email/check")
    public ResponseEntity<String> isAvailableEmail(@RequestBody UserEmailCheckRequest dto) {
        if (userService.findByEmail(dto.getEmail())) throw new UserException(UserErrorCode.USERNAME_ALREADY_EXISTS);

        return new ResponseEntity<>("사용가능한 이메일입니다.", HttpStatus.OK);
    }


    @Operation(
            summary = "내정보",
            description = "내정보를 불러옵니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "요청에 성공하였습니다."
    )
    @GetMapping("/me")
    @LoginUser
    public ResponseEntity<UserResponse> getMe() {
        final UserDetail userDetail = UserContextHolder.getContext();
        User user = userService.findById(userDetail.getId());

        return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
    }
}
