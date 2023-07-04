package com.themoment.everygsm.global.util;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.themoment.everygsm.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class S3Util {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    private final AmazonS3 amazonS3;

    private final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".png", ".jpeg");

    public String upload(MultipartFile multipartFile) {

            String fileName = createFileName(multipartFile.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try(InputStream inputStream = multipartFile.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket+"/logo", fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch(IOException e) {
                throw new CustomException("이미지 업로드 과정에서 에러가 발생하였습니다", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        return amazonS3.getUrl(bucket+"/logo", fileName).toString();
    }

    private ObjectMetadata extractMetaData(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        return objectMetadata;
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf("."));
            if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
                throw new CustomException("파일 확장자 형식이 잘못되었습니다.", HttpStatus.BAD_REQUEST);
            }
            return extension;
        } catch (StringIndexOutOfBoundsException e) {
            throw new CustomException("파일 확장자가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public void deleteS3(String fileName){
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    public void deleteLogo(String projectLogo, String creatorLogo) {
        deleteS3(projectLogo.substring(62));
        deleteS3(creatorLogo.substring(62));
    }
}
