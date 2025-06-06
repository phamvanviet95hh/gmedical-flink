package com.my_flink_job.servicve;

import com.my_flink_job.App;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class MyIcebergSink extends RichSinkFunction<String> {

    @Override
    public void open(Configuration parameters) throws Exception {

        // Tạo catalog nếu cần (chỉ tạo 1 lần)

    }

    @Override
    public void invoke(String objectPath, Context context) throws Exception {
        // Gọi hàm xử lý đọc MinIO và ghi Iceberg

    }
}
