package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class ChiTietDvkt {

    @JacksonXmlProperty(localName = "MA_LK")
    private String maLk;

    @JacksonXmlProperty(localName = "STT")
    private String stt;

    @JacksonXmlProperty(localName = "MA_DICH_VU")
    private String maDichVu;

    @JacksonXmlProperty(localName = "MA_PTTT_QT")
    private String maPtttQt;

    @JacksonXmlProperty(localName = "MA_VAT_TU")
    private String maVatTu;

    @JacksonXmlProperty(localName = "MA_NHOM")
    private String maNhom;

    @JacksonXmlProperty(localName = "GOI_VTYT")
    private String goiVtyt;

    @JacksonXmlProperty(localName = "TEN_VAT_TU")
    private String tenVatTu;

    @JacksonXmlProperty(localName = "TEN_DICH_VU")
    private String tenDichVu;

    @JacksonXmlProperty(localName = "MA_XANG_DAU")
    private String maXangDau;

    @JacksonXmlProperty(localName = "DON_VI_TINH")
    private String donViTinh;

    @JacksonXmlProperty(localName = "PHAM_VI")
    private String phamVi;

    @JacksonXmlProperty(localName = "SO_LUONG")
    private String soLuong;

    @JacksonXmlProperty(localName = "DON_GIA_BV")
    private String donGiaBv;

    @JacksonXmlProperty(localName = "DON_GIA_BH")
    private String donGiaBh;

    @JacksonXmlProperty(localName = "TT_THAU")
    private String ttThau;

    @JacksonXmlProperty(localName = "TYLE_TT_DV")
    private String tyleTtDv;

    @JacksonXmlProperty(localName = "TYLE_TT_BH")
    private String tyleTtBh;

    @JacksonXmlProperty(localName = "THANH_TIEN_BV")
    private String thanhTienBv;

    @JacksonXmlProperty(localName = "THANH_TIEN_BH")
    private String thanhTienBh;

    @JacksonXmlProperty(localName = "T_TRANTT")
    private String tTrantt;

    @JacksonXmlProperty(localName = "MUC_HUONG")
    private String mucHuong;

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

    @JacksonXmlProperty(localName = "T_BNTT")
    private String tBntt;

    @JacksonXmlProperty(localName = "T_BNCCT")
    private String tBncct;

    @JacksonXmlProperty(localName = "T_BHTT")
    private String tBhtt;

    @JacksonXmlProperty(localName = "MA_KHOA")
    private String maKhoa;

    @JacksonXmlProperty(localName = "MA_GIUONG")
    private String maGiuong;

    @JacksonXmlProperty(localName = "MA_BAC_SI")
    private String maBacSi;

    @JacksonXmlProperty(localName = "NGUOI_THUC_HIEN")
    private String nguoiThucHien;

    @JacksonXmlProperty(localName = "MA_BENH")
    private String maBenh;

    @JacksonXmlProperty(localName = "MA_BENH_YHCT")
    private String maBenhYhct;

    @JacksonXmlProperty(localName = "NGAY_YL")
    private String ngayYl;

    @JacksonXmlProperty(localName = "NGAY_TH_YL")
    private String ngayThYl;

    @JacksonXmlProperty(localName = "NGAY_KQ")
    private String ngayKq;

    @JacksonXmlProperty(localName = "MA_PTTT")
    private String maPttt;

    @JacksonXmlProperty(localName = "VET_THUONG_TP")
    private String vetThuongTp;

    @JacksonXmlProperty(localName = "PP_VO_CAM")
    private String ppVoCam;

    @JacksonXmlProperty(localName = "VI_TRI_TH_DVKT")
    private String viTriThDvkt;

    @JacksonXmlProperty(localName = "MA_MAY")
    private String maMay;

    @JacksonXmlProperty(localName = "MA_HIEU_SP")
    private String maHieuSp;

    @JacksonXmlProperty(localName = "TAI_SU_DUNG")
    private String taiSuDung;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;
}
