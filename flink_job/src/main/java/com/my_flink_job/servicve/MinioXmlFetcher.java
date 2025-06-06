package com.my_flink_job.servicve;

import io.minio.GetObjectArgs;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.json.JSONObject;
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

public class MinioXmlFetcher extends RichMapFunction<String, String> {
    static Logger logger1 = LoggerFactory.getLogger(MinioXmlFetcher.class);
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
    public String map(String objectKey) throws Exception {
        JSONObject json = new JSONObject(objectKey);
        // ✅ Lấy key từ JSON root level
        String key = json.getString("Key"); // Đây là dạng không encode
        // Tách bỏ tên bucket khỏi key nếu cần
        String objectPath = key.replace("gmedical.lake/", "");
        logger1.info("Key Minio: -----------------------------------------------------------------------> " + objectPath);
        // ✅ Tạo request với key đã tách

        InputStream inputStream = this.s3Client().getObject(GetObjectRequest.builder()
                .bucket("gmedical.lake")
                .key(objectPath)
                .build());

        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }
}
