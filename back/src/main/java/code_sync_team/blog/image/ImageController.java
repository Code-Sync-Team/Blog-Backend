package code_sync_team.blog.image;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/image")
@CrossOrigin
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
)
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        return imageService.saveImage(file);
    }
}
