package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CHI_TIEU_GIAYCHUYENTUYEN")
public class Xml13 {

    @JacksonXmlProperty(localName = "MA_LK")
    private String maLk;

    @JacksonXmlProperty(localName = "SO_HOSO")
    private String soHoSo;

    @JacksonXmlProperty(localName = "SO_CHUYENTUYEN")
    private String soChuyenTuyen;

    @JacksonXmlProperty(localName = "GIAY_CHUYEN_TUYEN")
    private String giayChuyenTuyen;

    @JacksonXmlProperty(localName = "MA_CSKCB")
    private String maCskcb;

    @JacksonXmlProperty(localName = "MA_NOI_DI")
    private String maNoiDi;

    @JacksonXmlProperty(localName = "MA_NOI_DEN")
    private String maNoiDen;

    @JacksonXmlProperty(localName = "HO_TEN")
    private String hoTen;

    @JacksonXmlProperty(localName = "NGAY_SINH")
    private String ngaySinh;

    @JacksonXmlProperty(localName = "GIOI_TINH")
    private String gioiTinh;

    @JacksonXmlProperty(localName = "MA_QUOCTICH")
    private String maQuocTich;

    @JacksonXmlProperty(localName = "MA_DANTOC")
    private String maDanToc;

    @JacksonXmlProperty(localName = "MA_NGHE_NGHIEP")
    private String maNgheNghiep;

    @JacksonXmlProperty(localName = "DIA_CHI")
    private String diaChi;

    @JacksonXmlProperty(localName = "MA_THE_BHYT")
    private String maTheBhyt;

    @JacksonXmlProperty(localName = "GT_THE_DEN")
    private String gtTheDen;

    @JacksonXmlProperty(localName = "NGAY_VAO")
    private String ngayVao;

    @JacksonXmlProperty(localName = "NGAY_VAO_NOI_TRU")
    private String ngayVaoNoiTru;

    @JacksonXmlProperty(localName = "NGAY_RA")
    private String ngayRa;

    @JacksonXmlProperty(localName = "DAU_HIEU_LS")
    private String dauHieuLs;

    @JacksonXmlProperty(localName = "CHAN_DOAN_RV")
    private String chanDoanRv;

    @JacksonXmlProperty(localName = "QT_BENHLY")
    private String qtBenhLy;

    @JacksonXmlProperty(localName = "TOMTAT_KQ")
    private String tomTatKq;

    @JacksonXmlProperty(localName = "PP_DIEUTRI")
    private String ppDieuTri;

    @JacksonXmlProperty(localName = "MA_BENH_CHINH")
    private String maBenhChinh;

    @JacksonXmlProperty(localName = "MA_BENH_KT")
    private String maBenhKt;

    @JacksonXmlProperty(localName = "MA_BENH_YHCT")
    private String maBenhYhct;

    @JacksonXmlProperty(localName = "TEN_DICH_VU")
    private String tenDichVu;

    @JacksonXmlProperty(localName = "TEN_THUOC")
    private String tenThuoc;

    @JacksonXmlProperty(localName = "PP_DIEU_TRI")
    private String ppDieuTriDuplicate;

    @JacksonXmlProperty(localName = "MA_LOAI_RV")
    private String maLoaiRv;

    @JacksonXmlProperty(localName = "MA_LYDO_CT")
    private String maLyDoCt;

    @JacksonXmlProperty(localName = "HUONG_DIEU_TRI")
    private String huongDieuTri;

    @JacksonXmlProperty(localName = "PHUONGTIEN_VC")
    private String phuongTienVc;

    @JacksonXmlProperty(localName = "HOTEN_NGUOI_HT")
    private String hoTenNguoiHt;

    @JacksonXmlProperty(localName = "CHUCDANH_NGUOI_HT")
    private String chucDanhNguoiHt;

    @JacksonXmlProperty(localName = "MA_BAC_SI")
    private String maBacSi;

    @JacksonXmlProperty(localName = "MA_TTDV")
    private String maTtdv;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;

}
