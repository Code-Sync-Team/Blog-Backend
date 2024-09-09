package code_sync_team.blog.image;

import code_sync_team.blog.global.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    public String saveImage(MultipartFile file) {
        return s3Service.uploadFile(file);
    }
}
