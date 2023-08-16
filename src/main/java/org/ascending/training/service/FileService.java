package org.ascending.training.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String clientRegion = "us-east-2";
    String stringObjKeyName = "sampleFile.txt";
    @Autowired
    AmazonS3 s3Client;

    public String uploadFile(String bucketName, MultipartFile file) throws IOException {
        if (file == null) {
            logger.error("Cannot upload a null file");
            return bucketName;
        }

        try {
            String uuid = UUID.randomUUID().toString();
            String originalFileName = file.getOriginalFilename();
            String newFileName = FilenameUtils.removeExtension(originalFileName) + uuid + "." + FilenameUtils.getExtension(originalFileName);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            s3Client.putObject(bucketName, newFileName, file.getInputStream(), objectMetadata);
            return getUrl(bucketName, file.getName());
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return bucketName;
    }

    private String getUrl(String bucketName, String s3Key) {
        return s3Client.getUrl(bucketName, s3Key).toString();
    }
}
