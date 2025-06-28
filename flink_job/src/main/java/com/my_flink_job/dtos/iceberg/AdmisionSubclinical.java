package com.my_flink_job.dtos.iceberg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmisionSubclinical {

    private String uuid;
    private String createdAt;
    private String updatedAt;
    private String donViDo;
    private String duPhong;
    private String giaTri;
    private String ketLuan;
    private String maBsDocKq;
    private String maChiSo;
    private String maDichVu;
    private String moTa;
    private String ngayKq;
    private String stt;
    private String tenChiSo;
    private String admision_checkin_uuid;

}
