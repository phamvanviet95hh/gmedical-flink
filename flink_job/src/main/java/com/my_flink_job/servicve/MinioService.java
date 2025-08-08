package com.my_flink_job.servicve;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;

import java.io.InputStream;

public class MinioService {

    private MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://10.6.8.29:9100")
                .credentials("LqvdamDwGIGdVh2pdU4t", "S2UG57kEiiyYw4WjeW3wEZTXgNi1OPmEWOssCYrZ")
                .build();
    }

    public InputStream downloadFile(String fullFilePath) throws Exception {
        try {
            var builder = GetObjectArgs.builder()
                    .bucket("gmedical.lake")
                    .object(fullFilePath)
                    .build();
            InputStream object = this.minioClient().getObject(builder);

            return object;
        } catch (Exception e) {
            throw new Exception("Error occurred: " + e);
        }
    }



}
