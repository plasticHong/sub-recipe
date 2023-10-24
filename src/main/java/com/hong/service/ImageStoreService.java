package com.hong.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.hong.eum.S3Location;
import com.hong.exception.CustomContentTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageStoreService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveImgToS3(MultipartFile file, S3Location s3Location) {

        contentTypeValidation(file);

        String fileName = String.valueOf(UUID.randomUUID());
        ObjectMetadata metadata = getObjectMetadataFromFile(file);
        InputStream inputStream = getInputStreamFromFile(file);

        amazonS3Client.putObject(bucket, s3Location.path + fileName, inputStream, metadata);

        return "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + s3Location.path + fileName;
    }

    private void contentTypeValidation(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null && !contentType.contains("image")) {
            throw new CustomContentTypeException();
        }
    }

    private InputStream getInputStreamFromFile(MultipartFile file) {
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            inputStream = InputStream.nullInputStream();
        }
        return inputStream;
    }

    private ObjectMetadata getObjectMetadataFromFile(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        return metadata;
    }


}

