package com.my_flink_job.servicve;

import com.my_flink_job.App;
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
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;


import java.io.InputStream;
import java.net.URI;

public class MinioS3Reader {
    static Logger logger1 = LoggerFactory.getLogger(MinioS3Reader.class);
    private S3Client s3Client() {
        return S3Client.builder()
                .httpClient(UrlConnectionHttpClient.builder().build())
                .endpointOverride(URI.create("http://10.6.8.29:9100"))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("LqvdamDwGIGdVh2pdU4t", "S2UG57kEiiyYw4WjeW3wEZTXgNi1OPmEWOssCYrZ")
                        )
                )
                .region(Region.US_EAST_1) // Bắt buộc, dù MinIO không sử dụng region
                .forcePathStyle(true)     // Bắt buộc với MinIO
                .build();
    }

    public InputStream downloadFile(String fullFilePath) throws Exception {
        try {
            JSONObject json = new JSONObject(fullFilePath);
            // ✅ Lấy key từ JSON root level
            String key = json.getString("Key"); // Đây là dạng không encode
            // Tách bỏ tên bucket khỏi key nếu cần
            String objectPath = key.replace("gmedical.lake/", "");
            logger1.info("Key Minio: -----------------------------------------------------------------------> " + objectPath);
            // ✅ Tạo request với key đã tách
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket("gmedical.lake")
                    .key(objectPath)
                    .build();

            ResponseInputStream<?> object = this.s3Client().getObject(getObjectRequest);

            return object;

        } catch (S3Exception e) {
            throw new Exception("S3Exception: " + e.awsErrorDetails().errorMessage(), e);
        } catch (Exception e) {
            throw new Exception("General exception: " + e.getMessage(), e);
        }
    }
}
