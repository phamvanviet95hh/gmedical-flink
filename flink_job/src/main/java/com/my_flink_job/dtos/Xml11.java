package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CHI_TIEU_DU_LIEU_GIAY_CHUNG_NHAN_NGHI_VIEC_HUONG_BAO_HIEM_XA_HOI")
public class Xml11 {

    @JacksonXmlProperty(localName = "MA_LK")
    private String maLk;

    @JacksonXmlProperty(localName = "SO_CT")
    private String soCt;

    @JacksonXmlProperty(localName = "SO_SERI")
    private String soSeri;

    @JacksonXmlProperty(localName = "SO_KCB")
    private String soKcb;

    @JacksonXmlProperty(localName = "DON_VI")
    private String donVi;

    @JacksonXmlProperty(localName = "MA_BHXH")
    private String maBhxh;

    @JacksonXmlProperty(localName = "MA_THE_BHYT")
    private String maTheBhyt;

    @JacksonXmlProperty(localName = "CHAN_DOAN_RV")
    private String chanDoanRv;

    @JacksonXmlProperty(localName = "PP_DIEUTRI")
    private String ppDieuTri;

    @JacksonXmlProperty(localName = "MA_DINH_CHI_THAI")
    private String maDinhChiThai;

    @JacksonXmlProperty(localName = "NGUYENNHAN_DINHCHI")
    private String nguyenNhanDinhChi;

    @JacksonXmlProperty(localName = "TUOI_THAI")
    private String tuoiThai;

    @JacksonXmlProperty(localName = "SO_NGAY_NGHI")
    private String soNgayNghi;

    @JacksonXmlProperty(localName = "TU_NGAY")
    private String tuNgay;

    @JacksonXmlProperty(localName = "DEN_NGAY")
    private String denNgay;

    @JacksonXmlProperty(localName = "HO_TEN_CHA")
    private String hoTenCha;

    @JacksonXmlProperty(localName = "HO_TEN_ME")
    private String hoTenMe;

    @JacksonXmlProperty(localName = "MA_TTDV")
    private String maTtdv;

    @JacksonXmlProperty(localName = "MA_BS")
    private String maBs;

    @JacksonXmlProperty(localName = "NGAY_CT")
    private String ngayCt;

    @JacksonXmlProperty(localName = "MA_THE_TAM")
    private String maTheTam;

    @JacksonXmlProperty(localName = "MAU_SO")
    private String mauSo;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;
}
