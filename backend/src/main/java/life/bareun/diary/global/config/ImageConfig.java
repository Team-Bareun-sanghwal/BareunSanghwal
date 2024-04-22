package life.bareun.diary.global.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

@Configuration
@RequiredArgsConstructor
public class ImageConfig {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    @Value("${cloud.aws.folder.tracker}")
    private String trackerFolderName;

    @Value("${cloud.aws.endpoint}")
    private String endPoint;

    public String uploadImage(MultipartFile image) {
        String originalFileName = image.getOriginalFilename();
        int dot = originalFileName.lastIndexOf(".");
        String fileType = originalFileName.substring(dot + 1);
        String randomFileName =
            trackerFolderName + "/" + UUID.randomUUID().toString() + "." + fileType;

        try {
            InputStream inputStream = image.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(image.getContentType());
            objectMetadata.setContentLength(image.getSize());
            amazonS3.putObject(new PutObjectRequest(bucketName, randomFileName,
                inputStream, objectMetadata).withCannedAcl(
                CannedAccessControlList.PublicRead));
            return endPoint + "/" + bucketName + "/" + randomFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteImage(String imageUrl) {
        int slash = imageUrl.lastIndexOf("/");
        String prefix = trackerFolderName + "/" + imageUrl.substring(slash + 1);
        try {
            amazonS3.deleteObject(bucketName, prefix);
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        }
    }


}
