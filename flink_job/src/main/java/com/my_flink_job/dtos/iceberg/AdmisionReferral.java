package com.my_flink_job.dtos.iceberg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmisionReferral {

    private String uuid;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String chanDoanRv;
    private String chucdanhNguoiHt;
    private String dauHieuLs;
    private String diaChi;
    private String duPhong;
    private String giayChuyenTuyen;
    private String gioiTinh;
    private String gtTheDen;
    private String hoTen;
    private String hotenNguoiHt;
    private String huongDieuTri;
    private String maBacSi;
    private String maBenhChinh;
    private String maBenhKt;
    private String maBenhYhct;
    private String maCskcb;
    private String maDantoc;
    private String maLoaiRv;
    private String maLydoCt;
    private String maNgheNghiep;
    private String maNoiDen;
    private String maNoiDi;
    private String maQuoctich;
    private String maTheBhyt;
    private String maTtdv;
    private String ngayRa;
    private String ngaySinh;
    private String ngayVao;
    private String ngayVaoNoiTru;
    private String phuongtienVc;
    private String ppDieuTri;
    private String qtBenhly;
    private String soChuyentuyen;
    private String soHoso;
    private String stt;
    private String tomtatKq;
    private String admision_checkin_uuid;
}
