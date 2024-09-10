package code_sync_team.blog.global.service;

import code_sync_team.blog.global.config.S3Config;
import code_sync_team.blog.image.exception.ImageErrorCode;
import code_sync_team.blog.image.exception.ImageException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.bucket}")
    private String bucket;


    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        try {
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ImageException(ImageErrorCode.SERVER_ERROR);
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();

    }
}
