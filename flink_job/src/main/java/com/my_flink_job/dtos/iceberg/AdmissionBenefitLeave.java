package com.my_flink_job.dtos.iceberg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionBenefitLeave {
    private String uuid;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String chanDoanRv;
    private String denNgay;
    private String donVi;
    private String duPhong;
    private String hoTenCha;
    private String hoTenMe;
    private String maBhxh;
    private String maBs;
    private String maDinhChiThai;
    private String maTheBhyt;
    private String maTheTam;
    private String maTtdv;
    private String mauSo;
    private String ngayCt;
    private String nguyennhanDinhchi;
    private String ppDieutri;
    private String soCt;
    private String soKcb;
    private String soNgayNghi;
    private String soSeri;
    private String stt;
    private String tuNgay;
    private String tuoiThai;
    private String admision_checkin_uuid;

}
