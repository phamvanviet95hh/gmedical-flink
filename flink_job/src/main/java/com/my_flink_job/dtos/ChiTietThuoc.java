package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class ChiTietThuoc {

    @JacksonXmlProperty(localName = "MA_LK")
    private String maLk;

    @JacksonXmlProperty(localName = "STT")
    private String stt;

    @JacksonXmlProperty(localName = "MA_THUOC")
    private String maThuoc;

    @JacksonXmlProperty(localName = "MA_NHOM")
    private String maNhom;

    @JacksonXmlProperty(localName = "TEN_THUOC")
    private String tenThuoc;

    @JacksonXmlProperty(localName = "DON_VI_TINH")
    private String donViTinh;

    @JacksonXmlProperty(localName = "HAM_LUONG")
    private String hamLuong;

    @JacksonXmlProperty(localName = "DUONG_DUNG")
    private String duongDung;

    @JacksonXmlProperty(localName = "LIEU_DUNG")
    private String lieuDung;

    @JacksonXmlProperty(localName = "SO_DANG_KY")
    private String soDangKy;

    @JacksonXmlProperty(localName = "MA_BTYT")
    private String maBtyt;

    @JacksonXmlProperty(localName = "SO_LUONG")
    private String soLuong;

    @JacksonXmlProperty(localName = "DON_GIA")
    private String donGia;

    @JacksonXmlProperty(localName = "TT_THAU")
    private String ttThau;

    @JacksonXmlProperty(localName = "TYLE_TT")
    private String tyleTt;

    @JacksonXmlProperty(localName = "THANH_TIEN")
    private String thanhTien;

    @JacksonXmlProperty(localName = "MA_KHOA")
    private String maKhoa;

    @JacksonXmlProperty(localName = "MA_BAC_SI")
    private String maBacSi;

    @JacksonXmlProperty(localName = "MA_BENH")
    private String maBenh;

    @JacksonXmlProperty(localName = "NGAY_YL")
    private String ngayYl;

    @JacksonXmlProperty(localName = "NGAY_KQ")
    private String ngayKq;

}
