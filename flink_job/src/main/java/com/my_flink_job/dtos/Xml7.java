package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CHI_TIEU_DU_LIEU_GIAY_RA_VIEN")
public class Xml7 {

    @JacksonXmlProperty(localName = "MA_LK")
    private String maLk;

    @JacksonXmlProperty(localName = "SO_LUU_TRU")
    private String soLuuTru;

    @JacksonXmlProperty(localName = "MA_YTE")
    private String maYte;

    @JacksonXmlProperty(localName = "MA_KHOA_RV")
    private String maKhoaRv;

    @JacksonXmlProperty(localName = "NGAY_VAO")
    private String ngayVao;

    @JacksonXmlProperty(localName = "NGAY_RA")
    private String ngayRa;

    @JacksonXmlProperty(localName = "MA_DINH_CHI_THAI")
    private String maDinhChiThai;

    @JacksonXmlProperty(localName = "NGUYENNHAN_DINHCHI")
    private String nguyenNhanDinhChi;

    @JacksonXmlProperty(localName = "THOIGIAN_DINHCHI")
    private String thoiGianDinhChi;

    @JacksonXmlProperty(localName = "TUOI_THAI")
    private String tuoiThai;

    @JacksonXmlProperty(localName = "CHAN_DOAN_RV")
    private String chanDoanRv;

    @JacksonXmlProperty(localName = "PP_DIEUTRI")
    private String ppDieuTri;

    @JacksonXmlProperty(localName = "GHI_CHU")
    private String ghiChu;

    @JacksonXmlProperty(localName = "MA_TTDV")
    private String maTtdv;

    @JacksonXmlProperty(localName = "MA_BS")
    private String maBs;

    @JacksonXmlProperty(localName = "TEN_BS")
    private String tenBs;

    @JacksonXmlProperty(localName = "NGAY_CT")
    private String ngayCt;

    @JacksonXmlProperty(localName = "MA_CHA")
    private String maCha;

    @JacksonXmlProperty(localName = "MA_ME")
    private String maMe;

    @JacksonXmlProperty(localName = "MA_THE_TAM")
    private String maTheTam;

    @JacksonXmlProperty(localName = "HO_TEN_CHA")
    private String hoTenCha;

    @JacksonXmlProperty(localName = "HO_TEN_ME")
    private String hoTenMe;

    @JacksonXmlProperty(localName = "SO_NGAY_NGHI")
    private String soNgayNghi;

    @JacksonXmlProperty(localName = "NGOAITRU_TUNGAY")
    private String ngoaiTruTuNgay;

    @JacksonXmlProperty(localName = "NGOAITRU_DENNGAY")
    private String ngoaiTruDenNgay;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;

}

