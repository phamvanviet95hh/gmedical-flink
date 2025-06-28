package com.my_flink_job.dtos.iceberg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmisionBirthCertificate {
    private String uuid;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String canNangCon;
    private String duPhong;
    private String ghiChu;
    private String gioiTinhCon;
    private String hoTenCha;
    private String hoTenCon;
    private String hoTenNnd;
    private String lanSinh;
    private String maBhxhNnd;
    private String maDantocNnd;
    private String maQuoctich;
    private String maTheNnd;
    private String maTheTam;
    private String maTtdv;
    private String mahuyenCuTru;
    private String matinhCuTru;
    private String maxaCuTru;
    private String ngayCt;
    private String ngaySinhCon;
    private String ngaycapCccdNnd;
    private String ngaysinhNnd;
    private String nguoiDoDe;
    private String nguoiGhiPhieu;
    private String noiCuTruNnd;
    private String noiSinhCon;
    private String noicapCccdNnd;
    private String quyenSo;
    private String sinhconDuoi32tuan;
    private String sinhconPhauthuat;
    private String so;
    private String soCccdNnd;
    private String soCon;
    private String soConSong;
    private String stt;
    private String tinhTrangCon;
    private String admision_checkin_uuid;
}
