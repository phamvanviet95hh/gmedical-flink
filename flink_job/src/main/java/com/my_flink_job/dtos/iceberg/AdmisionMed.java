package com.my_flink_job.dtos.iceberg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmisionMed {

    private String createdAt;
    private String updatedAt;

    private String stt;

    private String maThuoc;

    private String maPpCheBien;


    private String maCskcbThuoc;


    private String maNhom;


    private String tenThuoc;


    private String donViTinh;

    private String hamLuong;


    private String duongDung;


    private String dangBaoChe;


    private String lieuDung;
    private String cachDung;


    private String soDangKy;


    private String ttThau;

    private String phamVi;

    private String tyleTtBh;

    private String soLuong;

    private String donGia;


    private String thanhTienBv;


    private String thanhTienBh;


    private String tNguonKhacNsnn;


    private String tNguonKhacVtnn;


    private String tNguonKhacVttn;


    private String tNguonKhacCl;


    private String tNguonKhac;

    private String mucHuong;

    private String tBntt;


    private String tBncct;


    private String tBhtt;


    private String maKhoa;


    private String maBacSi;


    private String maDichVu;

    private String ngayYl;

    private String ngayThYl;


    private String maPttt;

    private String nguonCtra;

    private String vetThuongTp;

    private String duPhong;
    private String admision_checkin_uuid;
    private String maLk;
}
