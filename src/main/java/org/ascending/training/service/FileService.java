package org.ascending.training.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String clientRegion = "us-east-2";
    String bucketName = "tengfei-demo-s3";
    String stringObjKeyName = "sampleFile.txt";
    @Autowired
    AmazonS3 s3Client;

    public void uploadFile(File file) {
        if (file == null) {
            logger.error("Cannot upload a null file");
            return;
        }

        try {
            // Upload a text string as a new object.
            s3Client.putObject(bucketName, file.getName(), file);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }
}
