package com.my_flink_job.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileContentGdDto {
    private String fileName;
    private String pathFile;
    private GiamDinhHs content;
}
