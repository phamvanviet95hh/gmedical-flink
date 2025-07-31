package com.my_flink_job.dtos.iceberg;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessFiles {

    private String uuid;
    private String file_name;
    private String unit_name;
    private String directory;
    private String date_of_receipt_of_file;
    private String processed_at;
    private String etl_status;
    private String gmed_status;
    private String ma_lk;
    private String created_at;//
    private String updated_at;//

}
