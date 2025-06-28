package com.my_flink_job.dtos.iceberg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmisionDischarge {
    private String uuid;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String chanDoanRv;
    private String duPhong;
    private String ghiChu;
    private String hoTenCha;
    private String hoTenMe;
    private String maBs;
    private String maCha;
    private String maDinhChiThai;
    private String maKhoaRv;
    private String maMe;
    private String maTheTam;
    private String maTtdv;
    private String maYte;
    private String ngayCt;
    private String ngayRa;
    private String ngayVao;
    private String ngoaitruDenngay;
    private String ngoaitruTungay;
    private String nguyennhanDinhchi;
    private String ppDieutri;
    private String soLuuTru;
    private String soNgayNghi;
    private String stt;
    private String tenBs;
    private String thoigianDinhchi;
    private String tuoiThai;
    private String admision_checkin_uuid;
}
