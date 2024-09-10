package code_sync_team.blog.image;

import code_sync_team.blog.global.service.S3Service;
import code_sync_team.blog.image.exception.ImageErrorCode;
import code_sync_team.blog.image.exception.ImageException;
import code_sync_team.blog.user.UserRepository;
import code_sync_team.blog.user.exception.UserErrorCode;
import code_sync_team.blog.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    public String saveImage(MultipartFile file, Long userId) {

        String url = s3Service.uploadFile(file);

        if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png") && !file.getContentType().equals("image/gif")) {
            throw new ImageException(ImageErrorCode.IS_NOT_IMAGE);
        }


        Image image = Image.builder()
                .url(url)
                .user(userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.MEMBER_NOT_FOUND)))
                .build();

        imageRepository.save(image);

        return url;
    }
}
