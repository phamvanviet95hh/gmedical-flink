package com.my_flink_job.servicve;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my_flink_job.dtos.FileContentDto;
import io.minio.GetObjectArgs;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.json.JSONObject;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class MinioXmlFetcher extends RichFlatMapFunction<String, FileContentDto> {
    static Logger logger1 = LoggerFactory.getLogger(MinioXmlFetcher.class);
    ObjectMapper mapper = new ObjectMapper();
    private transient S3Client minioClient;

    private S3Client s3Client() {
        return S3Client.builder()
                .httpClient(UrlConnectionHttpClient.builder().build())
                .endpointOverride(URI.create("http://10.6.8.29:9100"))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("qHdHtnT6nwZqFydGe89l", "mYS5VJi5sr2Kx9o7lRnMmnxTQDLsJR6lppfoteef")
                        )
                )
                .region(Region.US_EAST_1) // Bắt buộc, dù MinIO không sử dụng region
                .forcePathStyle(true)     // Bắt buộc với MinIO
                .build();
    }

    @Override
    public void open(Configuration parameters) {
        minioClient = S3Client.builder()
                .httpClient(UrlConnectionHttpClient.builder().build())
                .endpointOverride(URI.create("http://10.6.8.29:9100"))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("qHdHtnT6nwZqFydGe89l", "mYS5VJi5sr2Kx9o7lRnMmnxTQDLsJR6lppfoteef")
                        )
                )
                .region(Region.US_EAST_1) // Bắt buộc, dù MinIO không sử dụng region
                .forcePathStyle(true)     // Bắt buộc với MinIO
                .build();
    }

    @Override
    public void flatMap(String objectKey, Collector<FileContentDto> out) {
        try {
            JSONObject json = new JSONObject(objectKey);
            String key = json.optString("Key", null);
            if (key == null || key.isEmpty()) {
                logger1.warn("No 'Key' found in: {}", objectKey);
                return;
            }

            String objectPath = key.replace("gmedical.lake/", "");
            String path = "gmedical.lake/"+ objectPath;
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            logger1.info("Key Minio: {}", objectPath);

            if (!objectPath.contains("3176")) {
                logger1.info("END ETL path Fail for 3176");
                return;
            }

            logger1.info("Start ETL for 3176");
            InputStream inputStream = this.s3Client().getObject(GetObjectRequest.builder()
                    .bucket("gmedical.lake")
                    .key(objectPath)
                    .build());

            String result = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);


            out.collect(FileContentDto.builder()
                    .pathFile(path)
                    .nameFile(fileName)
                    .content(result)
                    .build());

        } catch (Exception e) {
            logger1.error("Failed to process objectKey: {}", objectKey, e);
        }
    }
}
