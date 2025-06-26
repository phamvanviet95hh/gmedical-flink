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

    @JacksonXmlProperty(localName = "MA_PP_CHEBIEN")
    private String maPpCheBien;

    @JacksonXmlProperty(localName = "MA_CSKCB_THUOC")
    private String maCskcbThuoc;

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

    @JacksonXmlProperty(localName = "DANG_BAO_CHE")
    private String dangBaoChe;

    @JacksonXmlProperty(localName = "LIEU_DUNG")
    private String lieuDung;

    @JacksonXmlProperty(localName = "CACH_DUNG")
    private String cachDung;

    @JacksonXmlProperty(localName = "SO_DANG_KY")
    private String soDangKy;

    @JacksonXmlProperty(localName = "TT_THAU")
    private String ttThau;

    @JacksonXmlProperty(localName = "PHAM_VI")
    private String phamVi;

    @JacksonXmlProperty(localName = "TYLE_TT_BH")
    private String tyleTtBh;

    @JacksonXmlProperty(localName = "SO_LUONG")
    private String soLuong;

    @JacksonXmlProperty(localName = "DON_GIA")
    private String donGia;

    @JacksonXmlProperty(localName = "THANH_TIEN_BV")
    private String thanhTienBv;

    @JacksonXmlProperty(localName = "THANH_TIEN_BH")
    private String thanhTienBh;

    @JacksonXmlProperty(localName = "T_NGUONKHAC_NSNN")
    private String tNguonKhacNsnn;

    @JacksonXmlProperty(localName = "T_NGUONKHAC_VTNN")
    private String tNguonKhacVtnn;

    @JacksonXmlProperty(localName = "T_NGUONKHAC_VTTN")
    private String tNguonKhacVttn;

    @JacksonXmlProperty(localName = "T_NGUONKHAC_CL")
    private String tNguonKhacCl;

    @JacksonXmlProperty(localName = "T_NGUONKHAC")
    private String tNguonKhac;

    @JacksonXmlProperty(localName = "MUC_HUONG")
    private String mucHuong;

    @JacksonXmlProperty(localName = "T_BNTT")
    private String tBntt;

    @JacksonXmlProperty(localName = "T_BNCCT")
    private String tBncct;

    @JacksonXmlProperty(localName = "T_BHTT")
    private String tBhtt;

    @JacksonXmlProperty(localName = "MA_KHOA")
    private String maKhoa;

    @JacksonXmlProperty(localName = "MA_BAC_SI")
    private String maBacSi;

    @JacksonXmlProperty(localName = "MA_DICH_VU")
    private String maDichVu;

    @JacksonXmlProperty(localName = "NGAY_YL")
    private String ngayYl;

    @JacksonXmlProperty(localName = "NGAY_TH_YL")
    private String ngayThYl;

    @JacksonXmlProperty(localName = "MA_PTTT")
    private String maPttt;

    @JacksonXmlProperty(localName = "NGUON_CTRA")
    private String nguonCtra;

    @JacksonXmlProperty(localName = "VET_THUONG_TP")
    private String vetThuongTp;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;

}
