package com.example.blog.base.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.example.blog.base.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class FileUtils {
    public static final AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            .withRegion(Constant.AWS_CLIENT_REGION)
//            .withCredentials(new AWSStaticCredentialsProvider(
//                    new BasicAWSCredentials(Constant.AWS_SECRET_ID, Constant.AWS_SECRET_KEY)))
            .build();

    public static byte[] downloadPhoto(String key) throws IOException {
        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
        try {
            // Get an object and print its contents.
            System.out.println("Downloading an object");
            log.info("key is {}", key);
            fullObject = s3Client.getObject(new GetObjectRequest(Constant.AWS_BUCKET_NAME, key));
            System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
            return fullObject.getObjectContent().readAllBytes();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } finally {
//            // To ensure that the network connection doesn't remain open, close any open input streams.
            if (fullObject != null) {
                com.amazonaws.util.IOUtils.drainInputStream(fullObject.getObjectContent());
                fullObject.getObjectContent().abort();
            }
            if (objectPortion != null) {
                objectPortion.close();
            }
            if (headerOverrideObject != null) {
                headerOverrideObject.close();
            }
        }
        return null;
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println();
    }

    public static PutObjectResult uploadFile(String key, String filePath) {
        try {
            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(Constant.AWS_BUCKET_NAME, key, new File(filePath));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/png");
//            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            return s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return null;
    }

    public static String saveFile(String filepath, MultipartFile file) {
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        File dir = new File(filepath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File serverFile = new File(
                dir.getPath() + "/" + UUID.randomUUID() + "." + suffix);
        log.info(serverFile.getPath());
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return serverFile.getPath();
    }
}




