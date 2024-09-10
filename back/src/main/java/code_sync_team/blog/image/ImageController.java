package code_sync_team.blog.image;

import code_sync_team.blog.global.auth.LoginUser;
import code_sync_team.blog.global.auth.UserContextHolder;
import code_sync_team.blog.global.auth.UserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "Image", description = "이미지 관련 API")
@RequestMapping("/api/v1/image")
@CrossOrigin
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Operation(
            summary = "이미지 업로드",
            description = "이미지를 업로드 합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "이미지 업로드에 성공하였습니다."
    )
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
)
    @LoginUser
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        final UserDetail userDetail = UserContextHolder.getContext();
        Long userId = userDetail.getId();
        String url = imageService.saveImage(file, userId);

        return ResponseEntity.ok(url);
    }
}
