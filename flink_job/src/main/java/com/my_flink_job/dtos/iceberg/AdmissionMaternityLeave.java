package com.my_flink_job.dtos.iceberg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionMaternityLeave {

    private String uuid;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String maLk;


    private String soSeri;


    private String soCt;


    private String soNgay;


    private String donVi;


    private String chanDoanRv;


    private String tuNgay;


    private String denNgay;


    private String maTtdv;


    private String tenBs;


    private String maBs;


    private String ngayCt;

    private String duPhong;
    private String admision_checkin_uuid;
}
