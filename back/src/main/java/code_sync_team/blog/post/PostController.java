package code_sync_team.blog.post;


import code_sync_team.blog.global.auth.LoginUser;
import code_sync_team.blog.global.auth.UserContextHolder;
import code_sync_team.blog.global.auth.UserDetail;
import code_sync_team.blog.post.dto.PostCreateRequest;
import code_sync_team.blog.post.dto.PostCreateResponse;
import code_sync_team.blog.user.User;
import code_sync_team.blog.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "게시글 관련 API")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private PostService postService;

    @PostMapping
    @Operation(
            summary = "게시글 업로드",
            description = "게시글을 업로드 합니다."
    )
    @ApiResponse(
            responseCode = "201",
            description = "게시글 작성에 성공하였습니다."
    )
    @LoginUser
    public ResponseEntity<PostCreateResponse> createPost(@RequestBody PostCreateRequest request) {
        final UserDetail userDetail = UserContextHolder.getContext();
        Post post = postService.create(request.getTitle(), request.getContent(), userDetail.getId());

        PostCreateResponse response = new PostCreateResponse();
        response.title = post.getTitle();
        response.content = post.getContent();
        response.author = new UserResponse(post.getAuthor());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
