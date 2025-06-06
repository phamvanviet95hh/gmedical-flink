package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CHI_TIEU_DU_LIEU_TOM_TAT_HO_SO_BENH_AN")
public class Xml8 {

    @JacksonXmlProperty(localName = "MA_LK")
    private String maLk;

    @JacksonXmlProperty(localName = "MA_LOAI_KCB")
    private String maLoaiKcb;

    @JacksonXmlProperty(localName = "HO_TEN_CHA")
    private String hoTenCha;

    @JacksonXmlProperty(localName = "HO_TEN_ME")
    private String hoTenMe;

    @JacksonXmlProperty(localName = "NGUOI_GIAM_HO")
    private String nguoiGiamHo;

    @JacksonXmlProperty(localName = "DON_VI")
    private String donVi;

    @JacksonXmlProperty(localName = "NGAY_VAO")
    private String ngayVao;

    @JacksonXmlProperty(localName = "NGAY_RA")
    private String ngayRa;

    @JacksonXmlProperty(localName = "CHAN_DOAN_VAO")
    private String chanDoanVao;

    @JacksonXmlProperty(localName = "CHAN_DOAN_RV")
    private String chanDoanRv;

    @JacksonXmlProperty(localName = "QT_BENHLY")
    private String qtBenhLy;

    @JacksonXmlProperty(localName = "TOMTAT_KQ")
    private String tomTatKq;

    @JacksonXmlProperty(localName = "PP_DIEUTRI")
    private String ppDieuTri;

    @JacksonXmlProperty(localName = "NGAY_SINHCON")
    private String ngaySinhCon;

    @JacksonXmlProperty(localName = "NGAY_CONCHET")
    private String ngayConChet;

    @JacksonXmlProperty(localName = "SO_CONCHET")
    private String soConChet;

    @JacksonXmlProperty(localName = "KET_QUA_DTRI")
    private String ketQuaDieuTri;

    @JacksonXmlProperty(localName = "GHI_CHU")
    private String ghiChu;

    @JacksonXmlProperty(localName = "MA_TTDV")
    private String maTtdv;

    @JacksonXmlProperty(localName = "NGAY_CT")
    private String ngayCt;

    @JacksonXmlProperty(localName = "MA_THE_TAM")
    private String maTheTam;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;

}
